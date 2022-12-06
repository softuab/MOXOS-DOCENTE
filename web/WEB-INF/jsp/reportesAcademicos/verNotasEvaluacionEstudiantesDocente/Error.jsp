<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/png" href="<c:url value='/public/img/logouab.ico'/>"/> 
        <link href="<c:url value='/Public/Css/error.css'/>" rel="stylesheet" type="text/css"/>
        <title>Error</title>
    </head>
    <body>
        <div class="container">
            <div class="error">
                <h1></h1>
                <h2>ERROR</h2>
                <p>${mensaje} ;)</p>
            </div>
            <div class="stack-container">
                <div class="card-container">
                    <div class="perspec" style="--spreaddist: 125px; --scaledist: .75; --vertdist: -25px;">
                        <div class="card">
                            <div class="writing">
                                <div class="topbar">
                                    <div class="red"></div>
                                    <div class="yellow"></div>
                                    <div class="green"></div>
                                </div>
                                <div class="code">
                                    <ul>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-container">
                    <div class="perspec" style="--spreaddist: 100px; --scaledist: .8; --vertdist: -20px;">
                        <div class="card">
                            <div class="writing">
                                <div class="topbar">
                                    <div class="red"></div>
                                    <div class="yellow"></div>
                                    <div class="green"></div>
                                </div>
                                <div class="code">
                                    <ul>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-container">
                    <div class="perspec" style="--spreaddist:75px; --scaledist: .85; --vertdist: -15px;">
                        <div class="card">
                            <div class="writing">
                                <div class="topbar">
                                    <div class="red"></div>
                                    <div class="yellow"></div>
                                    <div class="green"></div>
                                </div>
                                <div class="code">
                                    <ul>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-container">
                    <div class="perspec" style="--spreaddist: 50px; --scaledist: .9; --vertdist: -10px;">
                        <div class="card">
                            <div class="writing">
                                <div class="topbar">
                                    <div class="red"></div>
                                    <div class="yellow"></div>
                                    <div class="green"></div>
                                </div>
                                <div class="code">
                                    <ul>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-container">
                    <div class="perspec" style="--spreaddist: 25px; --scaledist: .95; --vertdist: -5px;">
                        <div class="card">
                            <div class="writing">
                                <div class="topbar">
                                    <div class="red"></div>
                                    <div class="yellow"></div>
                                    <div class="green"></div>
                                </div>
                                <div class="code">
                                    <ul>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-container">
                    <div class="perspec" style="--spreaddist: 0px; --scaledist: 1; --vertdist: 0px;">
                        <div class="card">
                            <div class="writing">
                                <div class="topbar">
                                    <div class="red"></div>
                                    <div class="yellow"></div>
                                    <div class="green"></div>
                                </div>
                                <div class="code">
                                    <ul>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <script src="<c:url value='/Public/Js/error.js'/>" type="text/javascript"></script>
    </body>
</html>
