$(document).ready(function () {
    $('.tableX').tableX({index_from_left: 3, index_from_right: -1, index_from_top: -1});
    $("#idtipodenota").change(function () {
        var tipoevaluacion = $("#idtipodenota").val();
        if (tipoevaluacion == '') {
            $("#contenido").addClass("invisible")
            $("#alert").removeClass("invisible");
            $("#alert").addClass("visible")
            $("#alert").html("Debe seleccionar un tipo evaluacion");
        } else {
            $("#forma1").submit();
        }
    });
});