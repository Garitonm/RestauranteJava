/**
 * 
 */
var valores = [];
var idpessoa = 0;

function novo(){
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

    //mostra o formulário 
    form.style.display = "block";
    //esconde lista 
    lista.style.display = "none";

    //lista os inputs
    id = 0;
    var nome = document.getElementById("nome");
    var endereco = document.getElementById("endereco");
    var  tiporestaurante = document.getElementById("tiporestaurante");
    var  funcionamento = document.getElementById("funcionamento");
    var  pontuacao = document.getElementById("pontuacao");
    nome.value = "";
    endereco.value = "";
    tiporestaurante.value = "";
    funcionamento.value = "";
    pontuacao.value = "";
    
    //joga o foco no 1º campo:
    nome.focus();
}

function alterar(i){
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

    //mostra o formulário 
    form.style.display = "block";
    //esconde lista 
    lista.style.display = "none";

    //lista os inputs
    id = valores[i].id;
    var nome = document.getElementById("nome");
    var endereco = document.getElementById("endereco");
    var  tiporestaurante = document.getElementById("tiporestaurante");
    var  funcionamento = document.getElementById("funcionamento");
    var  pontuacao = document.getElementById("pontuacao");
    nome.value = valores[i].nome;
    endereco.value = valores[i].endereco;
    tiporestaurante.value = valores[i].tiporestaurante;
    funcionamento.value = valores[i].funcionamento;
  	pontuacao.value = valores[i].pontuacao;
    
    //joga o foco no 1º campo:
    nome.focus();
}

function salvar(){
	//valida campos obrigarotios
	if(document.getElementById("nome").value  == ""){
		alert("campo Nome é obrigaratório!");
		return;
	}
	
    //pega os dados digitados pelo usuário e monta um objeto
    var j = {
		id: id,
		nome:  document.getElementById("nome").value,
		endereco: document.getElementById("endereco").value,
		tiporestaurante: document.getElementById("tiporestaurante").value,
		funcionamento: document.getElementById("funcionamento").value,
		pontuacao: document.getElementById("pontuacao").value
	};
   
   	//define se o método sera para inserir ou alterar
   	if (id == 0) {
		   metodo = "POST";
	   } else {
		   metodo = "PUT";
	   }
   
	//envia os dados para o servidor
	mostraLoading("aguarde, salvando dados...")
	fetch("http://localhost:8080/RestauranteRest/Restaurante",
		{method: metodo,
	    body: JSON.stringify(j)
		}
	).then(resp => resp.json())
	.then(function (Retorno){
		escondeLoading();
		alert(Retorno.mensagem);
		
		var form = document.getElementById("formulario");
    	var lista = document.getElementById("lista");

    	//escondeo o formulário 
    	form.style.display = "none";
    	//mostra a lista 
    	lista.style.display = "block";
    	
    	// recarrega lista
    	listar();
    	
	});
    
}
function excluir(i){
 	id = valores[i].id; 
 

	//envia os dados para o servidor
	mostraLoading("aguarde, excluindo...");
	fetch("http://localhost:8080/RestauranteRest/Restaurante" + id,
		{method: "DELETE",
		}
	).then(resp => resp.json())
	.then(function (retorno){
		escondeLoading();
		alert(retorno.mensagem);
		
		var form = document.getElementById("formulario");
    	var lista = document.getElementById("lista");

    	//escondeo o formulário 
    	form.style.display = "none";
    	//mostra a lista 
    	lista.style.display = "block";
    	
    	// recarrega lista
    	listar();
    	
	});
    
}

function cancelar(){
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

    //escondeo o formulário 
    form.style.display = "none";
    //mostra a lista 
    lista.style.display = "block";
}

function listar(){
	var lista = document.getElementById("dados");
    //limpa a lista
    lista.innerHTML = "<tr><td colspan>aguarde, carregando...</td></tr>";
	
    fetch ("http://localhost:8080/RestauranteRest/Restaurante")
    .then(resp => resp.json())
    .then(dados => mostrar(dados));
}

function mostrar(dados){
	valores = dados;
    var lista = document.getElementById("dados");
    //limpa a lista
    lista.innerHTML ="";
    //percoorre a lista 
    for (var i in dados){
        lista.innerHTML += "<tr>"
                        + "<td>" + dados[i].id + "</td>"
                        + "<td>" + dados[i].nome + "</td>"
                        + "<td>" + dados[i].endereco + "</td>"
                        + "<td>" + dados[i].tiporestaurante + "</td>"
                        + "<td>" + dados[i].funcionamento + "</td>"
                        + "<td>" + dados[i].pontuacao + "</td>"
                        + "<td> <input type='button' value='A' onclick='alterar("+i+")'/></td>"
                        + "<td> <input type='button' value='X' onclick='excluir("+i+")'/>"
                        +"</td>"
                        + "</tr>";
                        
    }
}

function mostraLoading(msg){
	var loa = document.getElementById("loading");
    var con = document.getElementById("conteudo");
    loa.style.display = "block";
	con.style.display = "none";
	loa.innerHTML = msg;
}

function escondeLoading(){
	var loa = document.getElementById("loading");
    var con = document.getElementById("conteudo");
    loa.style.display = "none";
	con.style.display = "block";
	
}
listar();