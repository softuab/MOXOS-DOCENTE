/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.filter;

import com.google.javascript.jscomp.CompilationLevel;
import com.googlecode.htmlcompressor.compressor.ClosureJavaScriptCompressor;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.googlecode.htmlcompressor.compressor.YuiCssCompressor;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;

public class WhitespaceFilter implements Filter {

    private FilterConfig filterConfig;
    private static boolean compressCss = true;
    private static boolean compressJs = true;
    private static boolean removeIntertagSpaces = true;

    public WhitespaceFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            chain.doFilter(request, wrapResponse(httpServletResponse, createWriter(httpServletResponse)));
        } else {
            chain.doFilter(request, response);
        }

    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return The current filter configuration.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;

        if (filterConfig.getInitParameter("compressCss") != null) {
            compressCss = Boolean.parseBoolean(filterConfig.getInitParameter("compressCss"));
        }

        if (filterConfig.getInitParameter("compressJs") != null) {
            compressJs = Boolean.parseBoolean(filterConfig.getInitParameter("compressJs"));
        }

        if (filterConfig.getInitParameter("removeIntertagSpaces") != null) {
            removeIntertagSpaces = Boolean.parseBoolean(filterConfig.getInitParameter("removeIntertagSpaces"));
        }

    }

    /**
     * Wrap the given HttpServletResponse with the given PrintWriter.
     *
     * @param response The {@link HttpServletResponse} of which the given
     * {@link PrintWriter} have to be wrapped in.
     * @param writer The {@link PrintWriter} to be wrapped in the given
     * {@link HttpServletResponse}.
     * @return The {@link HttpServletResponse} with the {@link PrintWriter}
     * wrapped in.
     */
    private static HttpServletResponse wrapResponse(
            final HttpServletResponse response, final PrintWriter writer) {
        return new HttpServletResponseWrapper(response) {
            @Override
            public PrintWriter getWriter() throws IOException {
                return writer;
            }
        };
    }

    /**
     * Create a new {@link PrintWriter} for the given
     * {@link HttpServletResponse} which trims all whitespace using
     * {@link HtmlCompressor}.
     *
     * @param response The involved HttpServletResponse.
     * @return A PrintWriter which trims all whitespace.
     * @throws IOException If something fails at I/O level.
     */
    private static PrintWriter createWriter(final HttpServletResponse response)
            throws IOException {
        return new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"), false) {
            private final StringBuilder builder = new StringBuilder();
            HtmlCompressor compressor = new HtmlCompressor();
            ClosureJavaScriptCompressor cjsc = new ClosureJavaScriptCompressor(CompilationLevel.WHITESPACE_ONLY);
            YuiCssCompressor ycc = new YuiCssCompressor();

            @Override
            public void write(int c) {
                builder.append((char) c); // It is actually a char, not an int.
                if (builder.indexOf("</html>") > 0 || builder.indexOf("</partial-response>") > 0) {
                    flush();
                }
            }

            @Override
            public void write(char[] chars, int offset, int length) {
                builder.append(chars, offset, length);
                if (builder.indexOf("</html>") > 0 || builder.indexOf("</partial-response>") > 0) {
                    flush();
                }
            }

            @Override
            public void write(String string, int offset, int length) {
                builder.append(string, offset, length);
                if (builder.indexOf("</html>") > 0 || builder.indexOf("</partial-response>") > 0) {
                    flush();
                }
            }

            // Finally override the flush method so that it trims whitespace.
            @Override
            public void flush() {
                synchronized (builder) {

                    // Set compressor options
                    compressor.setRemoveIntertagSpaces(removeIntertagSpaces);
                    compressor.setCssCompressor(ycc);
                    compressor.setCompressCss(compressCss);
                    compressor.setJavaScriptCompressor(cjsc);
                    compressor.setCompressJavaScript(compressJs);

                    String html = compressor.compress(builder.toString());
                    try {
                        out.write(html);
                    } catch (IOException ex) {
                        Logger.getLogger(WhitespaceFilter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // Reset the local StringBuilder and issue real flush.
                    builder.setLength(0);
                    super.flush();
                }
            }
        };
    }

}