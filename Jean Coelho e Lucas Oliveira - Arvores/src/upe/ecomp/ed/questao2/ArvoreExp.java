package upe.ecomp.ed.questao2;


import java.util.StringTokenizer;

import upe.ecomp.ed.questao3.NoArvore;
import upe.ecomp.ed.questao3.Pilha;

public class ArvoreExp {
	private NoArvore raiz;

	public ArvoreExp(String e) {
		montarArvore(e);
	}

	public NoArvore getRaiz() {
		return raiz;
	}

	public void add(String e) {
		if (!contains(e)) {
			NoArvore novo = new NoArvore(e);
			if (raiz == null) {
				raiz = novo;
			} else {
				NoArvore aux = raiz;
				while (aux != null) {
					if (compare(aux.getInfo(), e) < 0) {
						if (aux.getDir() == null) {
							aux.setDir(novo);
							break;
						} else {
							aux = aux.getDir();
						}
					} else {
						if (aux.getEsq() == null) {
							aux.setEsq(novo);
							break;
						} else {
							aux = aux.getEsq();
						}
					}
				}
			}
		}
	}

	public boolean contains(String e) {
		boolean r = false;
		NoArvore aux = raiz;
		while (aux != null) {
			if (aux.getInfo().equals(e)) {
				r = true;
				break;
			} else if (compare(aux.getInfo(), e) < 0) {
				aux = aux.getDir();
			} else {
				aux = aux.getEsq();
			}
		}
		return r;
	}

	// se a < b retorna valor negativo
	// se a > b retorna valor positivo
	// caso contrario retorna 0
	private int compare(String a, String b) {
		int r = -1;
		if (a instanceof Comparable) {
			r = ((Comparable) a).compareTo(b);
		}
		return r;
	}

	public void montarArvore(String e) {
		Pilha<NoArvore> operador = new Pilha<NoArvore>();
		Pilha<NoArvore> valor = new Pilha<NoArvore>();
		Pilha<String> parenteses = new Pilha<String>();
		StringTokenizer teste = new StringTokenizer(e, " ");
		while (teste.hasMoreTokens()) {
			String temp = teste.nextToken();
			if (temp.equals("+") || temp.equals("-") || temp.equals("/") || temp.equals("*")) {
				operador.push(new NoArvore(temp));
			} else if (temp.equals("(")) {
				parenteses.push(temp);
			} else if (temp.equals(")")) {
				parenteses.pop();
				operador.topo().setDir(valor.pop());
				operador.topo().setEsq(valor.pop());
				valor.push(operador.pop());
			} else {
				valor.push(new NoArvore(temp));
			}

		}
		this.raiz = valor.topo();
	}

	
	// retorna o resultado da arvore de expressao em double
	public double calcula() {

		return Double.parseDouble(calcular(this.raiz).getInfo());
	}

	private NoArvore calcular(NoArvore no) {
		// primeiro pergunta se qual operador tem na raiz
		if (no.getInfo().equals("+")) {
			// depois pergunta se os valores esquerdos e direitos sao operadores
			// se for chama o propio metodo, se nao faz a operacao com os nos esquerdos e
			// direitos
			NoArvore a = no.getEsq();
			NoArvore b = no.getDir();
			if (a.getInfo().equals("+") || a.getInfo().equals("-") || a.getInfo().equals("*")
					|| a.getInfo().equals("/")) {
				a = calcular(a);
			}
			if (b.getInfo().equals("+") || b.getInfo().equals("-") || b.getInfo().equals("*")
					|| b.getInfo().equals("/")) {
				b = calcular(b);
			}

			return new NoArvore(Double.toString(Double.parseDouble(a.getInfo()) + Double.parseDouble(b.getInfo())));

		} else if (no.getInfo().equals("-")) {
			NoArvore a = no.getEsq();
			NoArvore b = no.getDir();
			if (a.getInfo().equals("+") || a.getInfo().equals("-") || a.getInfo().equals("*")
					|| a.getInfo().equals("/")) {
				a = calcular(a);
			}
			if (b.getInfo().equals("+") || b.getInfo().equals("-") || b.getInfo().equals("*")
					|| b.getInfo().equals("/")) {
				b = calcular(b);
			}

			return new NoArvore(Double.toString(Double.parseDouble(a.getInfo()) - Double.parseDouble(b.getInfo())));

		} else if (no.getInfo().equals("/")) {
			NoArvore a = no.getEsq();
			NoArvore b = no.getDir();
			if (a.getInfo().equals("+") || a.getInfo().equals("-") || a.getInfo().equals("*")
					|| a.getInfo().equals("/")) {
				a = calcular(a);
			}
			if (b.getInfo().equals("+") || b.getInfo().equals("-") || b.getInfo().equals("*")
					|| b.getInfo().equals("/")) {
				b = calcular(b);
			}

			return new NoArvore(Double.toString(Double.parseDouble(a.getInfo()) / Double.parseDouble(b.getInfo())));

		} else if (no.getInfo().equals("*")) {
			NoArvore a = no.getEsq();
			NoArvore b = no.getDir();
			if (a.getInfo().equals("+") || a.getInfo().equals("-") || a.getInfo().equals("*")
					|| a.getInfo().equals("/")) {
				a = calcular(a);
			}
			if (b.getInfo().equals("+") || b.getInfo().equals("-") || b.getInfo().equals("*")
					|| b.getInfo().equals("/")) {
				b = calcular(b);
			}

			return new NoArvore(Double.toString(Double.parseDouble(a.getInfo()) * Double.parseDouble(b.getInfo())));

		} else {
			// aqui ele retorna o proprio elemento pois entra no caso base de quando nao for
			// uma operacao
			return new NoArvore(no.getInfo());

		}

	}

	public void imprimir() {
		System.out.println("Imprimindo elementos em pre-ordem:");
		percursoPreOrdem(raiz);
		System.out.println("\n---------");
		System.out.println("Imprimindo elementos em ordem:");
		percursoEmOrdem(raiz);
		System.out.println("\n---------");
		System.out.println("Imprimindo elementos em ordem decrescente:");
		percursoEmOrdemDec(raiz);
		System.out.println("\n---------");
		System.out.println("Imprimindo elementos em pos-ordem:");
		percursoPosOrdem(raiz);
		System.out.println("\n---------");
	}

	private void percursoPreOrdem(NoArvore no) {
		if (no != null) {
			System.out.print(no.getInfo() + " ");
			percursoPreOrdem(no.getEsq());
			percursoPreOrdem(no.getDir());
		}
	}

	private void percursoPosOrdem(NoArvore no) {
		if (no != null) {
			percursoPosOrdem(no.getEsq());
			percursoPosOrdem(no.getDir());
			System.out.print(no.getInfo() + " ");
		}
	}

	private void percursoEmOrdem(NoArvore no) {
		if (no != null) {
			percursoEmOrdem(no.getEsq());
			System.out.print(no.getInfo() + " ");
			percursoEmOrdem(no.getDir());
		}
	}

	private void percursoEmOrdemDec(NoArvore no) {
		if (no != null) {
			percursoEmOrdemDec(no.getDir());
			System.out.print(no.getInfo() + " ");
			percursoEmOrdemDec(no.getEsq());
		}
	}

}