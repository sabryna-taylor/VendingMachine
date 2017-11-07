<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vending Machine</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1 align="center">Vending Machine</h1>
            <hr/>
            <ul class="list-group" id="errorMessages"></ul>
            <div class="row">

                <!-- Items Layout-->
                <div class="col-md-8">
                    <table class="table table-hover">
                        <tr class="col-md-4">
                            <c:forEach var="currentItem" items="${itemList}">                        
                            <button role="button" class="btn btn-default "                                  
                                    style="margin-bottom:50px; margin-right: 25px;">
                                <a href="selectItem?itemID=${currentItem.itemID}">
                                    <p align="left" align="top">
                                        <c:out value="${currentItem.itemID}"/>
                                    </p>
                                    <p align="center">
                                        <c:out value="${currentItem.itemName}"/>
                                    </p>
                                    <p align="center">
                                        $<c:out value="${currentItem.cost}"/>
                                    </p>                              
                                    <p align="center">
                                        Quantity Left: 
                                        <c:out value="${currentItem.quantity}"/>
                                    </p>
                                </a>
                            </button>
                        </c:forEach>

                        </tr>
                    </table>
                </div>

                <!--Forms-->
                <div class="col-md-4">

                    <!--Money in-->
                    <h3 align="center">Total $ In</h3>
                    <label for="add-money" class="col-md-2 control-label"></label>
                    <div class="col-md-8">
                        <input type="button" 
                               class="form-control"
                               id="add-money"
                               align="center"
                               value="${funds}"
                               required/>
                        <br/>
                    </div> 
                    <table class="table table-hover">
                        <div class="col-md-8 col-md-offset-2 text-center">
                            <form  id="add-dollar-button-form" role="form" action="addDollar"
                                   method="POST">
                                <tr>
                                <button type="submit"
                                        id="add-dollar-button"
                                        class="btn btn-default"
                                        align="middle"
                                        style="margin-right: 10px; margin-left: 75px; margin-bottom: 10px;">
                                    Add Dollar
                                </button>
                                </tr>
                            </form>

                            <form  id="add-dollar-button-form" role="form" action="addQuarter"
                                   method="POST">                            
                                <button type="submit"
                                        id="add-quarter-button"
                                        class="btn btn-default"
                                        style="margin-bottom: 10px;">
                                    Add Quarter
                                </button>
                            </form>
                        </div>                    

                        <div class="col-md-8 col-md-offset-2 text-center">
                            <form role="form" action="addDime"
                                  method="POST">
                                <tr>
                                <button type="submit"
                                        id="add-dime-button"
                                        class="btn btn-default"
                                        style="margin-right: 10px; margin-left: 80px;">
                                    Add Dime
                                </button>
                                </tr>
                            </form>
                            <form role="form" action="addNickel"
                                  method="POST">
                                <button type="submit"
                                        id="add-nickel-button"
                                        class="btn btn-default">
                                    Add Nickel
                                </button>
                            </form>
                        </div>
                    </table>
                    <hr/>

                    <!--Messages-->
                    <form class="form-horizontal">

                        <div class="form-group">
                            <h3 align="center">Messages</h3>
                            <label for="display-messages" class="col-md-2 control-label"></label>
                            <div class="col-md-8">
                                <input type="button" 
                                       class="form-control" 
                                       id="display-messages"
                                       value="${message}"
                                       required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="itemId" class="col-md-4 control-label">Item:</label>
                            <div class="col-md-6">
                                <input type="button" 
                                       class="form-control"
                                       value="${id}"
                                       required/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-4 col-md-offset-4 text-center">
                                <button type="submit"
                                        role="form" method="GET"
                                        onclick= 'action = "makePurchase?contactID=${id}"'
                                        id="make-purchase-button"
                                        class="btn btn-default">
                                    Make Purchase
                                </button>
                            </div>
                        </div>
                    </form>
                    <hr/>

                    <!--Change-->
                    <form class="form-horizontal" role="form" action="returnChange"
                          method="POST">
                        <div class="form-group">
                            <h3 align="center">Change</h3>
                            <label for="display-change" class="col-md-2 control-label"></label>
                            <div class="col-md-8">
                                <input type="button" 
                                       class="form-control" 
                                       id="display-change"
                                       value = "${change}"
                                       required/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-4 col-md-offset-4 text-center">
                                <button type="submit"
                                        id="change-return-button"
                                        class="btn btn-default">
                                    Change Return
                                </button>
                            </div>
                        </div>
                    </form>

                </div>

            </div>   
        </div>
    </div>
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>

