package Trabalho.MVC.Visao;

import Trabalho.MVC.Controle.ClienteControle;
import Trabalho.MVC.Controle.FilmeControle;
import Trabalho.MVC.Controle.IngressoControle;
import Trabalho.MVC.Controle.VendaControle;
import Trabalho.MVC.Visao.AbstractTables.AbstractTableCliente;
import Trabalho.MVC.Visao.AbstractTables.AbstractTableFilme;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TelaPrincipal extends JFrame {

    private ClienteControle ControleCliente;
    private FilmeControle ControleFilme;
    private IngressoControle ControleIngresso;
    private VendaControle ControleVenda;

    private JMenuBar BarraMenu;
    private JMenu MenuCliente;
    private JMenu MenuFilme;
    private JMenu MenuIngresso;
    private JMenu MenuVenda;
    private JMenuItem CInserir, FInserir, IInserir, VRealizar;
    private JMenuItem CExcluir, FExcluir, IExcluir, VExcluir;

    private JPanel panel;
    private TelaInsCliente telaInsCliente;
    private TelaRemCliente telaRemCliente;
    private AbstractTableCliente ModeloTabelaCliente;
    private AbstractTableFilme ModeloTabelaFilme;
    private TelaInsFilme telaInsFilme;
    private TelaRemFilme telaRemFilme;

    public TelaPrincipal() {
        super("Tela Principal");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        String diretorio = null;

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            diretorio = "MVC\\Visao\\imgs\\icone.jpg";
        }
        else if (System.getProperty("os.name").toLowerCase().contains("nix") || System.getProperty("os.name").toLowerCase().contains("nux") ||
                System.getProperty("os.name").toLowerCase().contains("aix")){
            diretorio = "MVC/Visao/imgs/icone.jpg";
        }

        ImageIcon iconePrincipal = new ImageIcon(diretorio);
        setIconImage(iconePrincipal.getImage());

        this.ControleCliente = new ClienteControle();
        ModeloTabelaCliente = new AbstractTableCliente(ControleCliente);
        this.ControleFilme = new FilmeControle();
        ModeloTabelaFilme = new AbstractTableFilme(ControleFilme);
        this.ControleIngresso = new IngressoControle(ControleFilme);
        this.ControleVenda = new VendaControle(ControleIngresso, ControleCliente);
        telaInsCliente = new TelaInsCliente(ControleCliente, this, ModeloTabelaCliente);
        telaRemCliente = new TelaRemCliente(ControleCliente, ModeloTabelaCliente);
        telaInsFilme = new TelaInsFilme(ControleFilme, this, ModeloTabelaFilme);
        telaRemFilme = new TelaRemFilme(ControleFilme, ModeloTabelaFilme);

        UIManager.put("MenuBar.border", new LineBorder(Color.BLACK));
        UIManager.put("MenuItem.background", Color.BLACK);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ControleCliente.AtualizarArquivo();
                ControleFilme.AtualizarArquivo();
            }
        });

        CInserir = new JMenuItem("Inserir Cliente");
        CInserir.setForeground(Color.WHITE);
        CInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                telaInsCliente.setVisible(true);
                telaInsCliente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                telaInsCliente.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent wEvent) {
                        setVisible(true);
                    }
                });
            }
        });

        CExcluir = new JMenuItem("Remover Cliente");
        CExcluir.setForeground(Color.WHITE);
        CExcluir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                telaRemCliente.setVisible(true);
                telaRemCliente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                telaRemCliente.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent wEvent) {
                        setVisible(true);
                    }
                });
            }
        });

        FInserir = new JMenuItem("Inserir Filme");
        FInserir.setForeground(Color.WHITE);
        FInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                telaInsFilme.setVisible(true);
                telaInsFilme.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                telaInsFilme.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent wEvent) {
                        setVisible(true);
                    }
                });
            }
        });

        FExcluir = new JMenuItem("Remover Filme");
        FExcluir.setForeground(Color.WHITE);
        FExcluir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                telaRemFilme.setVisible(true);
                telaRemFilme.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                telaRemFilme.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent wEvent) {
                        setVisible(true);
                    }
                });
            }
        });


        IInserir = new JMenuItem("Inserir Ingresso(s)");
        IInserir.setForeground(Color.WHITE);
        IExcluir = new JMenuItem("Remover Ingresso(s)");
        IExcluir.setForeground(Color.WHITE);

        VRealizar = new JMenuItem("Realizar Venda");
        VRealizar.setForeground(Color.WHITE);
        VExcluir = new JMenuItem("Remover Venda");
        VExcluir.setForeground(Color.WHITE);

        MenuCliente = new JMenu("Para Clientes");
        MenuCliente.add(CInserir);
        MenuCliente.add(CExcluir);
        MenuCliente.setForeground(Color.WHITE);

        MenuFilme = new JMenu("Para Filmes");
        MenuFilme.add(FInserir);
        MenuFilme.add(FExcluir);
        MenuFilme.setForeground(Color.WHITE);

        MenuIngresso = new JMenu("Para Ingressos");
        MenuIngresso.add(IInserir);
        MenuIngresso.add(IExcluir);
        MenuIngresso.setForeground(Color.WHITE);

        MenuVenda = new JMenu("Para Vendas");
        MenuVenda.add(VRealizar);
        MenuVenda.add(VExcluir);
        MenuVenda.setForeground(Color.WHITE);

        BarraMenu = new JMenuBar();
        BarraMenu.add(MenuCliente);
        BarraMenu.add(MenuFilme);
        BarraMenu.add(MenuIngresso);
        BarraMenu.add(MenuVenda);

        BarraMenu.setPreferredSize(new Dimension(600, 30));
        BarraMenu.setLayout(new GridBagLayout());
        BarraMenu.setBackground(Color.BLACK);
        setJMenuBar(BarraMenu);

        BufferedImage imagem = null;

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            diretorio = "MVC\\Visao\\imgs\\img_cinema.jpg";
        }
        else if (System.getProperty("os.name").toLowerCase().contains("nix") || System.getProperty("os.name").toLowerCase().contains("nux") ||
                System.getProperty("os.name").toLowerCase().contains("aix")){
            diretorio = "MVC/Visao/imgs/img_cinema.jpg";
        }

        try {
            imagem = ImageIO.read(new File(diretorio));
        }
        catch (IOException ex){ ex.printStackTrace(); }

        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JLabel picLabel = new JLabel(new ImageIcon(imagem));
        panel.add(picLabel);
        add(panel, BorderLayout.CENTER);
    }

}
