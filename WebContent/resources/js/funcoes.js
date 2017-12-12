$(document).ready(function(){
	var lancamento = $('#lancamento_input');
	lancamento.inputmask({'alias':'date','placeholder':'dd/mm/aaaa'});
});

function validarNumero(input){
	var texto = input.value;
	input.value = texto.replace(/[^0-9]+/g,'');
	//if (!isNaN(String.fromCharCode(window.event.keyCode))) return true; else return false;
}

function configurarMoeda(){
	$(".moeda").maskMoney({decimal: ",", thousands: ".", allowZero: true, prefix: "R$ "});
}

function configurarDecimal(input){
	id = "#"+input.id;
	$(id).maskMoney({decimal: ",", thousands: ".", allowZero: true});
}

function mostrarPainel(input, opcao){
	if(opcao == true){
		input.show();
	}else{
		input.hide();
	}
}

function selectText(input){
	id = "#"+input.id;
	$(id).select();
}
