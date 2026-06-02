package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int linha;
	private final int coluna;

	private boolean aberto;
	private boolean minado;
	private boolean marcado;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha; // true se diferente 
		boolean colunaDiferente  = coluna != vizinho.coluna; // true se diferente
		boolean diagonal = linhaDiferente && colunaDiferente; // true se ambas true
		
		int deltaLinha = Math.abs(linha - vizinho.linha); // valor absoluto de linha - linha (1 || 0)
		int deltaColuna = Math.abs(coluna - vizinho.coluna);// valor absolute de coluna - coluna (1 || 0) 
		int deltaGeral = deltaColuna + deltaLinha;// linha + coluna = 1 vizinho(cruz) se = 2 vizinho diagonal 
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho); // adiciona vizinho na lista cruz
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho); // adiciona vizinho na lista diagonal
			return true;
		} else {
			return false;
		}
	}
	
	void alternarMarcacao() { 
		if(!aberto) { //se nao aberto
			marcado = !marcado;// se estiver false retorna true, se estiver true retornar false
		}
	}
	
	boolean abrir() {
		if(!aberto && !marcado) {
			aberto = true;//abre se estiver nao aberto e nao marcado
			
			if(minado) { //explode se estiver minado
				// TODO
			}
			if(vizinhancaSegura()) { //se metodo vizinhança segura true
				vizinhos.forEach(v -> v.abrir()); //abre todos vizinhos enquanto v segura
			}
			return true;
		}else {
			return false;			
		}
		
	}

	boolean vizinhancaSegura() { //abre os campos seguros
		return vizinhos.stream().noneMatch(v -> v.minado); // expessao lambda onde se nenhum vizinho cair no predicado a vizinhança é segura(false retorna pelo menos uma mina, não segura) 
	}

	void minar() {
		minado = true;	
	}
	
	public boolean isMinado() {
		return minado;
	}
	public boolean isMarcado() {//metodo get para boolean é is
		return marcado;
	}

	public boolean isAberto() {//metodo get para boolean é is
		return aberto;
	}
	
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isFechado() {
		return !aberto;
	}
	
	public int getLinha() {
		return linha;
	}
	
	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	long minasVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count(); // descobre quantas minas existem na vizinhança 
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
}

