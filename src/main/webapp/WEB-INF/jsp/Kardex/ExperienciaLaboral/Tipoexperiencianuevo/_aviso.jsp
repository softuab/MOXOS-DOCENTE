<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="row">
    <div class="col">
        <div class="container">
            <div class="row">
                <div class="col-sm-6 mx-auto">
                    <div class="card border-secondary mb-3">
                        <div class="card-header"><i class="fa fa-exclamation-triangle"></i>&nbsp;&nbsp;AVISO
                            !!
                        </div>
                        <div class="card-body text-secondary">
                            <c:out value="${mensaje}"/><br/>
                        </div>
                        <div class="card-footer text-muted text-center">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> 
