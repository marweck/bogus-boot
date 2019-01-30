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
 * <p>
 * Example declaring on a web.xml file
 *
 * <pre>
 * <filter>
 *   <filter-name>SubnetFilter</filter-name>
 *   <filter-class>com.acme.web.SubnetFilter</filter-class>
 * </filter>
 *
 * <filter-mapping>
 *   <filter-name>SubnetFilter</filter-name>
 *   <url-pattern>/metrics</url-pattern>
 * </filter-mapping>
 * </pre>
 */
public class SubnetFilter implements Filter {

    private BigInteger mask;

    private BigInteger subnet;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        InetAddress ip = InetAddress.getByName(servletRequest.getRemoteAddr());
        BigInteger remote = ipToNumber(ip);

        //are both IPs on the same subnet?
        boolean sameSubnet = subnet.equals(remote.and(mask));

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
    private static BigInteger calculateMask(InetAddress localHost) throws SocketException {

        InterfaceAddress interfaceAddress = getInterfaceAddress(localHost);

        //8, 16, 24...
        short prefixLength = interfaceAddress.getNetworkPrefixLength();

        int length = interfaceAddress.getAddress().getAddress().length;

        return BigInteger.valueOf(Long.MAX_VALUE << ((length * 8) - prefixLength));
    }

    /**
     * Locates the interface address of the given InetAddress
     */
    private static InterfaceAddress getInterfaceAddress(InetAddress localHost) throws SocketException {

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
    private static BigInteger ipToNumber(InetAddress ip) {

        byte[] bytes = ip.getAddress();

        return new BigInteger(1, bytes);
    }

    public static void main(String[] args) throws UnknownHostException, SocketException {

        InetAddress ip = InetAddress.getByName("192.168.0.1");
//        InetAddress ip = InetAddress.getByName("2001:db8:c001:ba40::1");
        System.out.println(ipToNumber(ip));

        InetAddress ip2 = InetAddress.getByName("192.168.0.2");
//        InetAddress ip2 = InetAddress.getByName("2001:db8:c001:ba40::2");
        System.out.println(ipToNumber(ip2));

        InetAddress localHost = InetAddress.getLocalHost();

        BigInteger mask = calculateMask(localHost);

        BigInteger localAddress = ipToNumber(ip);
        BigInteger remote = ipToNumber(ip2);

        System.out.println((localAddress.and(mask)).equals(remote.and(mask)));
    }

    @Override
    public void destroy() {
    }
}