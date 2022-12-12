package Trabalho.MVC.Persistencia;

import Trabalho.MVC.Modelo.Entidade;
import Trabalho.MVC.Modelo.Filme;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;


public class FilmeP implements Persistencia {

    private static FilmeP instancia;

    private FilmeP(){}

    public static synchronized FilmeP getinstancia(){
        if (instancia == null) instancia = new FilmeP();
        return instancia;
    }
    private LinkedList<Entidade> ListadeFilmes = new LinkedList<>();

    String caminho = System.getProperty("user.dir") + "\\PersistenciaFilmes.json";
    File BancoDeDadosFilme = new File(caminho);

    public void InicializarArquivo(boolean choice){
        if (choice) CarregarArquivo();
    }

    public void Inserir(Entidade entidade){
        if (ListadeFilmes.size() == 0) {
            entidade.setId(1);
        } else {
            entidade.setId(ListadeFilmes.get(
                   ListadeFilmes.size() - 1).getId() + 1);
        }

        ListadeFilmes.add( (Filme) entidade );
    }

    public void Excluir(Entidade entidade){
        ListadeFilmes.remove((Filme) entidade);
        for (int i = 0; i < ListadeFilmes.size(); i++) ListadeFilmes.get(i).setId(i + 1);
    }

    public void AtualizarArquivo(){
        if (ListadeFilmes.size() != 0) {
            if (!BancoDeDadosFilme.exists()) {
                try {
                    BancoDeDadosFilme.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            Gson gson = new Gson();
            String json = gson.toJson(ListadeFilmes);
            FileWriter arq = null;
            try {
                arq = new FileWriter(caminho);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            PrintWriter gravador = new PrintWriter(arq);

            gravador.println(json);

            try {
                arq.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
            if (BancoDeDadosFilme.exists()) BancoDeDadosFilme.delete();
        }
    }


    public LinkedList<Entidade> CarregarArquivo(){
        if(!BancoDeDadosFilme.exists()) return ListadeFilmes;
        String conteudoObjetos = null;
        try {conteudoObjetos = Files.readString(Paths.get(caminho));}
        catch (IOException exception) {exception.printStackTrace();}
        Gson gson = new Gson();
        Type tipoListadeFilmes = new TypeToken<LinkedList<Filme>>() {
        }.getType();
        ListadeFilmes = gson.fromJson(conteudoObjetos, tipoListadeFilmes);
        return ListadeFilmes;
    }

    public Filme BuscarId(int id) {
        for (Entidade listadeFilme : ListadeFilmes) {
            if (listadeFilme.getId() == id) {
                return (Filme) listadeFilme;
            }
        }
        return null;
    }

    public Filme BuscarString(String string) {
        for (Entidade listadeFilme : ListadeFilmes) {
            if (((Filme) listadeFilme).getTitulo().equalsIgnoreCase(string)) {
                return (Filme) listadeFilme;
            }
        }
        return null;
    }

}