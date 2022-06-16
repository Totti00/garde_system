$(document).ready(function(){
    var ultimostato;
    var fotoresLvl;
    var tempLvl;
    var time;
    var manMode;

    function updateVal(){
        $.getJSON("http://localhost:8080/api/data", function(result){
            for(i=0;i< result.length;i++){
                ultimostato=result[result.length-1]["stato"];
                fotoresLvl=result[result.length-1]["fotoResLvl"];
                tempLvl=result[result.length-1]["tempLvl"];
                time=result[result.length-1]["time"];
                manMode=result[result.length-1]["manMode"];
                $("h1").html("Stato: "+ ultimostato);
                $("h2").html("Temperature: "+tempLvl);
                $("h3").html("Fotoresistor: "+fotoresLvl);
                $("h4").html("Manual mode: "+manMode);
            }        
        });
    }
    updateVal();
});