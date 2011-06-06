package org.jahia.modules.codeHighlighter.filter;

import org.apache.commons.lang.StringUtils;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.filter.AbstractFilter;
import org.jahia.services.render.filter.RenderChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Charles Flond
 * Date: 23/05/11
 * Time: 11:27
 * To change this template use File | Settings | File Templates.
 */
public class CodeFilter  extends AbstractFilter {

    private transient static Logger logger = LoggerFactory.getLogger(CodeFilter.class);

    private Pattern openTagPattern;
    private String brushesRegexp;
    private String closingTagRegexp;
    private Map brushesMapping = new HashMap<String,String>();
    private Map brushes = new HashMap<String,String>();


    @Override
    public String execute(String previousOut, RenderContext renderContext, Resource resource, RenderChain chain) throws Exception {

        /*Check if the buffer is not empty*/
        if (StringUtils.isEmpty(previousOut)) {
            return previousOut;
        }

        /*Initliaze some counter and status*/
        long timer = System.currentTimeMillis();
        boolean evaluated = false;

        /*Check if we can find a code tag in the HTML buffer*/
        Matcher matcher = openTagPattern.matcher(previousOut);

        /*If we have some code tags, let's operate*/
        while (matcher.find()) {

            /*Set evaluate to true for logs*/
            evaluated = true;

            //Replace the code tag by the pre + code tags with proper classes
            previousOut = matcher.replaceFirst("<pre class=\"code\">" + "<code class=\""+matcher.group(2)+"\">");

            //Push the code language in the map
            brushes.put(matcher.group(2),brushesMapping.get(matcher.group(2)));

            //Continue to search for other code tags in the buffer
            matcher = openTagPattern.matcher(previousOut);
        }

        //Create a list of brushes to retrieve based on the code languages used in the page
        String brushesList = "";
        Iterator<String> keySetIterator= brushes.keySet().iterator();
        while (keySetIterator.hasNext())
              brushesList = brushesList + brushes.get(keySetIterator.next()) + ",";

        if(!brushesList.isEmpty())
        {
            brushesList = brushesList.substring(0,brushesList.length()-1);

            //Replace the brushes tag in the HTML buffer by the brushes list
            previousOut = previousOut.replaceAll(brushesRegexp, brushesList);
        }

        //Replace all the closing code tags in the HTML buffer
        previousOut = previousOut.replaceAll(closingTagRegexp,"</code></pre>");

        //Log for debugging
        if (evaluated && logger.isDebugEnabled()) {
            logger.debug("Time to replace the code {} ms", (System.currentTimeMillis() - timer));
        }
        return previousOut;

    }

    /*Setters for SPRING*/
    public void setOpenTagRegexp(String regexp) {
        this.openTagPattern = Pattern.compile(regexp);
    }

     public void setClosingTagRegexp(String regexp) {
        this.closingTagRegexp = regexp;
    }

    public void setBrushesMapping(Map brushesMapping) {
        this.brushesMapping = brushesMapping;
    }

    public void setBrushesRegexp(String brushesRegexp) {
        this.brushesRegexp = brushesRegexp;
    }
}
