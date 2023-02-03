package com.minone.bogus.web;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet filter that allows requests coming from same subnet only.
 * <p>
 * Conversion from InetAddress to Long: https://stackoverflow.com/a/34881294
 */
public class SubnetFilter implements Filter {

    private BigInteger mask;

    private BigInteger subnet;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        try {
            InetAddress localHost = InetAddress.getLocalHost();

            this.mask = calculateMask(localHost);

            BigInteger localAddress = ipToNumber(localHost);

            this.subnet = localAddress.and(mask);
        } catch (UnknownHostException | SocketException e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain)
            throws IOException, ServletException {

        InetAddress ip = InetAddress.getByName(servletRequest.getRemoteAddr());
        BigInteger remote = ipToNumber(ip);

        // are both IPs on the same subnet?
        boolean sameSubnet = subnet.equals(remote.and(mask));

        if (sameSubnet) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Origin not allowed");
        }
    }

    /**
     * Calculates the IP maks of the given localhost address
     */
    private static BigInteger calculateMask(final InetAddress localHost) throws SocketException {

        InterfaceAddress interfaceAddress = getInterfaceAddress(localHost);

        // 8, 16, 24...
        short prefixLength = interfaceAddress.getNetworkPrefixLength();

        int length = interfaceAddress.getAddress().getAddress().length;

        return BigInteger.valueOf(Long.MAX_VALUE << ((length * 8) - prefixLength));
    }

    /**
     * Locates the interface address of the given InetAddress
     */
    private static InterfaceAddress getInterfaceAddress(final InetAddress localHost) throws SocketException {

        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);

        if (networkInterface == null) {
            throw new IllegalStateException("Network interface address not found!!!");
        }

        for (InterfaceAddress addr : networkInterface.getInterfaceAddresses()) {

            if (addr.getAddress().equals(localHost)) {
                return addr;
            }
        }

        throw new IllegalStateException("Network interface address not found!!!");
    }

    /**
     * Converts InetAddress to a number representing the given IP
     */
    private static BigInteger ipToNumber(final InetAddress ip) {

        byte[] bytes = ip.getAddress();

        return new BigInteger(1, bytes);
    }

    @Override
    public void destroy() {
    }
}