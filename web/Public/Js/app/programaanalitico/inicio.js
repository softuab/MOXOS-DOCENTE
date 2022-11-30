document.addEventListener("DOMContentLoaded", function () {
    $('.preloader-background').delay(1700).fadeOut('slow');
    $('.loader').delay(1700).fadeOut();
});
var paso2 = new Anno({
    target: '#v-pills-justificacion',
    position: 'left',
    content: '<strong>JUSTIFICACION</strong><br>Debe contener una explicacion clara y precisa del contenido teorico abordar y su vinculo con el perfil profesional. Asi mismo debe valorarse lo apropiado de la ubicacion de la signatura describiendo las relaciones con otras asignaturas y su nivel de profundidad. Se exige que la justificacion este relacionada con los objetivos de la asignatura y con el perfil profesional. Se debe dar respuesta a las siguientes preguntas &#191;Que problemas profesionales ayuda a resolver la asignatura? &#191;Por qu&#233; resulta necesario su estudio?.',
    buttons: [{text: 'Salir', click: function (anno, evt) {
                anno.hide();
            }
        }]
});
var pasol = new Anno({
    target: '#v-pills-marco_referencial',
    position: 'left',
    content: '<strong>MARCO REFERENCIAL</strong><br>Determinaciones existentes en el dise&#241;o curricular de la carrera que resultan significativas para el dise&#241;o del plan global de la asignatura.',
    buttons: [{text: 'Salir', click: function (anno, evt) {
                anno.hide();
            }
        }]
});
var paso3 = new Anno({
    target: '#v-pills-propositos',
    position: 'left',
    content: '<strong>PROP&#211;SITOS</strong><br>Se expresar&#225; de manera concreta la intenci&#243;n que se persigue al impartir la asignatura.',
    buttons: [{text: 'Salir', click: function (anno, evt) {
                anno.hide();
            }
        }]
});
var paso4 = new Anno({
    target: '#v-pills-objetivo_desarrollador',
    position: 'left',
    content: '<strong>OBJETIVO GENERAL DE LA ASIGNATURA</strong><br> Destaca la capacidad que se quiere lograr en los alumnos y los conocimientos esenciales en que se sustenta dicha capacidad.',
    buttons: [{text: 'Salir', click: function (anno, evt) {
                anno.hide();
            }
        }]
});
var paso5 = new Anno({
    target: '#v-pills-objetivo_educativo',
    position: 'left',
    content: '<strong>OBJETIVO EDUCATIVO</strong><br>Destaca la convicci&#243;n a formar en el estudiante.',
    buttons: [{text: 'Salir', click: function (anno, evt) {
                anno.hide();
            }
        }]
});
var paso6 = new Anno({
    target: '#v-pills-metodos_estrategias',
    position: 'left',
    content: '<strong>MODALIDAD Y METODOLOG&#205;A A UTILIZAR EN EL PEA</strong><br>Se exige que la metodolog&#237;a propuesta se adecue a las condiciones de los estudiantes y del contexto, se fundamente en concepciones pedag&#243;gicas modernas, el uso de las TICs y en particular permitir el desarrollo de habilidades y destrezas en los estudiantes.',
    buttons: [{text: 'Salir', click: function (anno, evt) {
                anno.hide();
            }
        }]
});
var paso7 = new Anno({
    target: '#v-pills-recursos',
    position: 'left',
    content: '<strong>RECURSOS EDUCATIVOS Y TECNOL&#211;GICOS</strong><br>Se debe proponer el uso de recursos did&#225;cticos adecuados a los contenidos de la asignatura.Espec&#237;menes, modelos, esquemas, l&#225;minas, CD, documentos compartidos, as&#237; como, materiales, instrumentos, insumos, maquinarias, computadoras, programas de software, internet y equipos que se requieren para el desarrollo de la asignatura. Son los medios de ense&#241;anzas.',
    buttons: [{text: 'Salir', click: function (anno, evt) {
                anno.hide();
            }
        }]
});
var paso8 = new Anno({
    target: '#v-pills-sistema_evaluacion',
    position: 'left',
    content: '<strong>COMPONENTES DEL SISTEMA DE EVALUACI&#211;N </strong><br>La cantidad y tipo de evaluaciones a realizar (parcial, practica, prueba final, etc,), los productos terminales a presentar por los estudiantes en cada evaluaci&#243;n, el o los productos terminales a presentar de cada asignatura, los procedimientos de evaluaci&#243;n a utilizar y la ponderaci&#243;n de los diferentes tipos de ex&#225;menes o evaluaci&#243;n.',
    buttons: [{text: 'Salir', click: function (anno, evt) {
                anno.hide();
            }
        }]
});
var paso9 = new Anno({
    target: '#v-pills-sistema_evaluacion_criterio',
    position: 'left',
    content: '<strong>CRITERIOS DE EVALUACI&#211;N (REQUISITOS M&#205;NIMOS DE APROBACI&#211;N </strong><br>Al menos, debe describirse cualitativamente los conocimientos, habilidades y valores indispensables para considerar a un alumno aprobado.<br> <strong>Nota:</strong> Obtener  51 puntos en la calificaci&#243;n final de la asignatura, no es un criterio de evaluaci&#243;n. Los criterios de evaluaci&#243;n se adecuan a las exigencias de los objetivos de la asignatura.',
    buttons: [{text: 'Salir', click: function (anno, evt) {
                anno.hide();
            }
        }]
});

var paso10 = new Anno([{
        target: '#contenido',
        position: 'left',
        content: '<strong>CONTENIDOS</strong><br>Para cada tema o Unidad Tem&#225;tica. <br> Los contenidos de las asignaturas deben estar actualizados y reflejar los conocimientos cient&#237;ficos y conocimientos acad&#233;micos. ',
        buttons: [AnnoButton.NextButton, AnnoButton.DoneButton]
    }, {
        target: '#objetivo_instructivo',
        position: 'left',
        content: '<strong>OBJETIVOS INSTRUCTIVOS</strong><br>Destaca la habilidad a formar en el estudiante, as&#237; como al objeto de estudio (Ciencia/s que sustenta/n la habilidad). Redactado en t&#233;rminos de aprendizaje del estudiante.<br>1.	Objetivo Instructivo del Tema 1.<br>2.	Etc. .<br>Debe observarse una relaci&#243;n de sistema entre los Objetivos Instructivos y el Objetivo general. As&#237; mismo, entre los objetivos de la asignatura con los objetivos del &#225;rea y del a&#241;o o semestre al que pertenece. .<br>Los objetivos deben ser redactados de forma clara. .<br>Los objetivos de las unidades tem&#225;ticas deben permitir cumplir, como sistema, el objetivo General de la asignatura.',
        buttons: [AnnoButton.BackButton, AnnoButton.NextButton]
    }, {
        target: '#conocimientos',
        position: 'left',
        content: '<strong>SISTEMA DE CONOCIMIENTOS</strong><br>Conceptos, leyes y teor&#237;as que se ense&#241;an y aprenden Pormenorizadas.',
        buttons: [AnnoButton.BackButton, AnnoButton.NextButton]
    }, {
        target: '#habilidades',
        position: 'left',
        content: '<strong>SISTEMA DE HABILIDADES</strong><br>Sistema de Acciones que componen la habilidad declarada en el objetivo.',
        buttons: [AnnoButton.BackButton, AnnoButton.NextButton]
    }, {
        target: '#valores',
        position: 'left',
        content: '<strong>SISTEMA DE VALORES</strong><br>Valores y sentimientos factibles de tratar en el desarrollo del tema.',
        buttons: [AnnoButton.BackButton, AnnoButton.DoneButton]
    }]);
function iniciartutorial() {
    pasol.show();
}