package com.minone.bogus.web

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException
import java.math.BigInteger
import java.net.*

/**
 * Servlet filter that allows requests coming from same subnet only.
 *
 * Conversion from InetAddress to Long: https://stackoverflow.com/a/34881294
 */
class SubnetFilter : Filter {
    private var mask: BigInteger? = null
    private var subnet: BigInteger? = null

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        try {
            val localHost = InetAddress.getLocalHost()
            mask = calculateMask(localHost)
            val localAddress = ipToNumber(localHost)
            subnet = localAddress.and(mask)
        } catch (e: UnknownHostException) {
            throw ServletException(e)
        } catch (e: SocketException) {
            throw ServletException(e)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        servletRequest: ServletRequest,
        servletResponse: ServletResponse,
        filterChain: FilterChain
    ) {
        val ip = InetAddress.getByName(servletRequest.remoteAddr)
        val remote = ipToNumber(ip)

        // are both IPs on the same subnet?
        val sameSubnet = subnet == remote.and(mask)
        if (sameSubnet) {
            filterChain.doFilter(servletRequest, servletResponse)
        } else {
            val response = servletResponse as HttpServletResponse
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Origin not allowed")
        }
    }

    override fun destroy() {}

    companion object {
        /**
         * Calculates the IP maks of the given localhost address
         */
        @Throws(SocketException::class)
        private fun calculateMask(localHost: InetAddress): BigInteger {
            val interfaceAddress = getInterfaceAddress(localHost)

            // 8, 16, 24...
            val prefixLength = interfaceAddress.networkPrefixLength
            val length = interfaceAddress.address.address.size
            return BigInteger.valueOf(Long.MAX_VALUE shl length * 8 - prefixLength)
        }

        /**
         * Locates the interface address of the given InetAddress
         */
        @Throws(SocketException::class)
        private fun getInterfaceAddress(localHost: InetAddress): InterfaceAddress {
            val networkInterface = NetworkInterface.getByInetAddress(localHost)
                ?: throw IllegalStateException("Network interface address not found!!!")
            for (addr in networkInterface.interfaceAddresses) {
                if (addr.address == localHost) {
                    return addr
                }
            }
            throw IllegalStateException("Network interface address not found!!!")
        }

        /**
         * Converts InetAddress to a number representing the given IP
         */
        private fun ipToNumber(ip: InetAddress): BigInteger {
            val bytes = ip.address
            return BigInteger(1, bytes)
        }
    }
}