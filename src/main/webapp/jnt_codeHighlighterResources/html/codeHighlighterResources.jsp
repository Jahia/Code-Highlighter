<%@ page import="org.jahia.services.render.RenderContext" %>
<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="utility" uri="http://www.jahia.org/tags/utilityLib" %>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="functions" uri="http://www.jahia.org/tags/functions" %>
<%@ taglib prefix="uiComponents" uri="http://www.jahia.org/tags/uiComponentsLib" %>
<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
<%--@elvariable id="out" type="java.io.PrintWriter"--%>
<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
<%--@elvariable id="workspace" type="java.lang.String"--%>
<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>
<%--@elvariable id="acl" type="java.lang.String"--%>


<%--Load the library--%>
<template:addResources type="javascript" resources="jquery.js,jquery.beautyOfCode.js" />

<%--Initialize the jquery plugin--%>
<template:addResources >
    <script type="text/javascript">
        $(document).ready(function() {
            var brushesList = document.getElementById("brushes").innerHTML;
            var brushesTable = brushesList.split(",");
            $.beautyOfCode.init({
                brushes: brushesTable,
                ready: function() {
                    $.beautyOfCode.beautifyAll();
                },
                baseUrl: '${url.currentModule}/',
                scripts: 'javascript/',
                styles: 'css/'
            })
        });
    </script>
</template:addResources>

<%--Hidden div to retrieve the list of brushes--%>
<div id="brushes" style="display:none;">aabrushesaa</div>

<%--In edit mode display a message to allow editing this component--%>
<c:if test="${renderContext.editMode}">
    Edit the Code Highlighter<br/>
	Activate brushes : aabrushesaa
</c:if>