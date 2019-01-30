package com.minone.bogus.web;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Servlet filter that allows requests coming from same subnet only.
 * <p>
 * Conversion from InetAddress to Long: https://stackoverflow.com/a/34881294
 */
public class SubnetFilter implements Filter {

    private long mask;

    private long subnet;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        try {
            InetAddress localHost = InetAddress.getLocalHost();

            this.mask = calculateMask(localHost);

            long localAddress = ipToNumber(localHost);

            this.subnet = localAddress & mask;

        } catch (UnknownHostException | SocketException e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        InetAddress ip = InetAddress.getByName(servletRequest.getRemoteAddr());
        long remote = ipToNumber(ip);

        //are both IPs on the same subnet?
        boolean sameSubnet = subnet == (remote & mask);

        if (sameSubnet) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Origin not allowed");
        }
    }

    /**
     * Calculates the IP maks of the given localhost address
     */
    private static long calculateMask(InetAddress localHost) throws SocketException {

        InterfaceAddress interfaceAddress = getInterfaceAddress(localHost);

        //8, 16, 24...
        short prefixLength = interfaceAddress.getNetworkPrefixLength();

        int length = interfaceAddress.getAddress().getAddress().length;

        return Long.MAX_VALUE << ((length * 8) - prefixLength);
    }

    /**
     * Locates the interface address of the given InetAddress
     */
    private static InterfaceAddress getInterfaceAddress(InetAddress localHost) throws SocketException {

        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);

        for (InterfaceAddress addr : networkInterface.getInterfaceAddresses()) {

            if (addr.getAddress().equals(localHost)) {
                return addr;
            }
        }

        throw new IllegalStateException("Network interface address not found!!!");
    }

    /**
     * Converts InetAddress to a long representing the given IP
     */
    private static long ipToNumber(InetAddress ip) {

        byte[] bytes = ip.getAddress();

        return new BigInteger(1, bytes).longValue();
    }

    @Override
    public void destroy() {
    }
}