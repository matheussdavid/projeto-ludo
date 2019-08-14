/**
 * Implementa as mecânicas e regras do jogo Ludo.
 *
 * @author Alan Moraes / alan@ci.ufpb.br
 * @author Victor Koehler / koehlervictor@cc.ci.ufpb.br
 */
public class Jogo {

    // Tabuleiro do jogo
    private final Tabuleiro tabuleiro;
    
    // Dados do jogo.
    private final Dado[] dados;
    
    //O primeiro jogador a jogar será o verde;
    private int jogador;
    
    //Variável para verificar se o jogador rolou os dados antes de tentar mover uma peça
    private boolean dadosRolados;
    
    //Variável para verificar se os dados rolados possuem o mesmo valor
    private boolean dadosIguais;
    
    //Variável para verificar se os dados rolados possuem o mesmo valor
    private boolean travarDados;

    /**
     * Construtor padrão do Jogo Ludo.
     * Isto é, um jogo de Ludo convencional com dois dados.
     */
    public Jogo() {
        this(2);
    }
    
    /**
     * Construtor do Jogo Ludo para inserção de um número arbitrário de dados.
     * @param numeroDados Número de dados do jogo.
     */
    public Jogo(int numeroDados) {
        this.tabuleiro = new Tabuleiro();
        this.dados = new Dado[numeroDados];
        jogador = 1;
        dadosRolados = false;
        travarDados = false;
        
        for (int i = 0; i < this.dados.length; i++) {
            // remover parâmetro do construtor para dado não batizado
            this.dados[i] = new Dado(i);
        }

        inicializaJogo();
    }

    /**
     * Construtor do Jogo Ludo para inserção de dados arbitrários.
     * Útil para inserir dados "batizados" e fazer testes.
     * @param dados Dados
     */
    public Jogo(Dado[] dados) {
        this.tabuleiro = new Tabuleiro();
        this.dados = dados;
        assert dados.length > 0; // TO BE REMOVED

        inicializaJogo();
    }
    
    private void inicializaJogo() {

        // AQUI SUGERE-SE QUE SE INSIRA A INICIALIZAÇÃO DO JOGO
        // ISTO É, A INSERÇÃO DAS PEÇAS NO TABULEIRO E AS DEFINIÇÕES DOS CAMPOS
        // SE NECESSÁRIO, MODIFIQUE A ASSINATURA DO MÉTODO
        
        carregarGuaritas();        
        
        //
        // TRECHO DE EXEMPLO
        //
        
        // Vamos inicializar a guarita verde colocando as 4 peças do jogador verde nela.
        //
        // Guarita = espaço onde fica as peças fora do jogo;
        // Consulte a imagem "referencia.png" acompanhada na pasta dessa classe.
        
        // Obtemos uma das peças verdes que inicializamos logo acima para usa-la como exemplo.
        // Movemos ela para a casa de inicio do jogador verde.
        Casa casaGuarita;
        Casa casaInicio;
        Peca peca;
                
        /*guaritaVerde = tabuleiro.getGuarita("VERDE");
        casaGuarita = guaritaVerde.getCasa(3);
        peca = casaGuarita.getPeca();
        casaInicio = tabuleiro.getCasaInicio("VERDE");
        peca.mover(casaInicio);*/
        
        // Apenas como um exemplo adicional, colocamos uma peça azul no tabuleiro.
        peca = new Peca("VERDE");
        casaInicio = tabuleiro.getCasaInicio("VERDE");
        peca.mover(casaInicio);
        
        peca = new Peca("AZUL");
        casaInicio = tabuleiro.getCasaInicio("AZUL");
        peca.mover(casaInicio);
        
        peca = new Peca("AMARELO");
        casaInicio = tabuleiro.getCasaInicio("AMARELO");
        peca.mover(casaInicio);
        
        peca = new Peca("VERMELHO");
        casaInicio = tabuleiro.getCasaInicio("VERMELHO");
        peca.mover(casaInicio);
    }

    /**
     * Método invocado pelo usuário através da interface gráfica ou da linha de comando para jogar os dados.
     * Aqui deve-se jogar os dados e fazer todas as verificações necessárias.
     */
    public void rolarDados() {
        // AQUI SE IMPLEMENTARÁ AS REGRAS DO JOGO.
        // TODA VEZ QUE O USUÁRIO CLICAR NO DADO DESENHADO NA INTERFACE GRÁFICA,
        // ESTE MÉTODO SERÁ INVOCADO.
        
        
        //
        // TRECHO DE EXEMPLO
        //
        
        if(travarDados == false)
        {
        // Aqui percorremos cada dado para lançá-lo individualmente.   
        for (Dado dado : dados) {
            dado.rolar();
        }
        dadosRolados = true;
        dadosIguais = verificaDadosIguais();  
        }
        travarDados = true;
    }
    
    /**
     * Método invocado pelo usuário através da interface gráfica ou da linha de comando quando escolhida uma peça.
     * Aqui deve-se mover a peça e fazer todas as verificações necessárias.
     * @param casa Casa escolhida pelo usuário/jogador.
     */
    public void escolherCasa(Casa casa) {

        // AQUI SE IMPLEMENTARÁ AS REGRAS DO JOGO.
        // TODA VEZ QUE O USUÁRIO CLICAR EM UMA CASA DESENHADA NA INTERFACE GRÁFICA,
        // ESTE MÉTODO SERÁ INVOCADO.
        
        
        //
        // TRECHO DE EXEMPLO
        //
        
        // Perguntamos à casa se ela possui uma peça. 
        // Se não possuir, não há nada para se fazer.
        if (!casa.possuiPeca()) {
            return;
        }
        
        // Perguntamos à casa qual é a peça.
        Peca peca = casa.getPeca();

        // Percorremos cada dado, somando o valor nele à variável somaDados.
        int somaDados = 0;
        for (Dado dado : dados) {
            somaDados += dado.getValor();
        }
        
        // Percorreremos N casas.
        Casa proximaCasa = casa;
        if(jogadorDaVez(jogador) == peca.obterCor() &&dadosRolados == true)
        {
            if(proximaCasa.getCasaSegura() != null && peca.obterCor() != proximaCasa.getCor())
            {
                Casa teste = proximaCasa.getCasaSegura();
                if(teste.getCor() == peca.obterCor())
                {
                    for (int i = 0; i < somaDados && proximaCasa!= null; i++) 
                    {
                        proximaCasa = proximaCasa.getCasaSegura();
                        proximaCasa.setCasaSegura(proximaCasa);
                    } 
                }
                else
                {
                    for (int i = 0; i < somaDados && proximaCasa != null; i++) 
                    {
                        proximaCasa = proximaCasa.getCasaSeguinte();
                    }    
                }
            }
            else if(proximaCasa.getCasaSegura() != null && peca.obterCor() == casa.getCor())
            {
                for (int i = 0; i < somaDados && proximaCasa!= null; i++) 
                {
                    proximaCasa = proximaCasa.getCasaSeguinte();
                }
            }
            else
            {
                for (int i = 0; i < somaDados && proximaCasa != null; i++) 
                {
                    proximaCasa = proximaCasa.getCasaSeguinte();
                }  
            }
            if(jogador == 4)
            {
                if(dadosIguais != true){
                    jogador = 1;                    
                }
                dadosRolados = false;
                travarDados = false;
            }
            else
            {
                if(dadosIguais != true){
                    jogador++;
                }
                dadosRolados = false;
                travarDados = false;
            }
        }
        
      

        if (proximaCasa != null) {
            // Finalmente, a variável casaN contém a casa que a peça deve ser inserida.
            peca.mover(proximaCasa);
        }
        else {
            // // NÃO HÁ PRÓXIMA CASA!
            // // FIM DO JOGO? A PEÇA ESTÁ NA GUARITA?
            // // Descomente a próxima linha para ser notificado quando isso acontecer:
            // System.err.println("Não há próxima casa!");
        
            // // Descomente as duas próximas linhas para verificar se a peça está na guarita:
            // if (casa.pertenceGuarita())
            //     System.out.println("A peça está na guarita");
        }
    }
    
    /**
     * Retorna o jogador que deve jogar os dados ou escolher uma peça.
     * @return Cor do jogador.
     */
    public String getJogadorDaVez() {
        return null;
    }
    
    /**
     * O tabuleiro deste jogo.
     * @return O tabuleiro deste jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * Retorna o i-ésimo dado deste jogo entre 0 (inclusivo) e N (exclusivo).
     * Consulte obterQuantidadeDeDados() para verificar o valor de N
     * (isto é, a quantidade de dados presentes).
     * @param i Indice do dado.
     * @return O i-ésimo dado deste jogo.
     */
    public Dado getDado(int i) {
        return dados[i];
    }
    
    /**
     * Obtém a quantidade de dados sendo usados neste jogo.
     * @return Quantidade de dados.
     */
    public int getQuantidadeDados() {
        return dados.length;
    }
    
    
    /**
     * Carrega as guaritas com as peças dentro delas
     */
    public void carregarGuaritas(){
        Guarita guaritaVerde;        
        guaritaVerde = tabuleiro.getGuarita("VERDE");
        for (Casa casaGuarita : guaritaVerde.obterTodasAsCasas()) {
            Peca novaPeca = new Peca("VERDE");
            novaPeca.mover(casaGuarita);
        }
        
        Guarita guaritaVermelha;        
        guaritaVermelha = tabuleiro.getGuarita("VERMELHO");
        for (Casa casaGuarita : guaritaVermelha.obterTodasAsCasas()) {
            Peca novaPeca = new Peca("VERMELHO");
            novaPeca.mover(casaGuarita);
        }
        
        Guarita guaritaAzul;        
        guaritaAzul = tabuleiro.getGuarita("AZUL");
        for (Casa casaGuarita : guaritaAzul.obterTodasAsCasas()) {
            Peca novaPeca = new Peca("AZUL");
            novaPeca.mover(casaGuarita);
        }
        
        Guarita guaritaAmarela;        
        guaritaAmarela = tabuleiro.getGuarita("AMARELO");
        for (Casa casaGuarita : guaritaAmarela.obterTodasAsCasas()) {
            Peca novaPeca = new Peca("AMARELO");
            novaPeca.mover(casaGuarita);
        }
    }
    
     /**
     * Retorna o jogador que deve jogar os dados ou escolher uma peça.
     * @return Cor do jogador.
     */
    public String jogadorDaVez(int nJogador)
    {
        String jogadorDaVez= " ";
        if(nJogador == 1)
        {
            jogadorDaVez = "VERDE";
        }
        else if(nJogador == 2)
        {
            jogadorDaVez = "VERMELHO";
        }
        else if(nJogador == 3)
        {
            jogadorDaVez = "AZUL";
        }
        else if(nJogador == 4)
        {
            jogadorDaVez = "AMARELO";
        }
        return jogadorDaVez;
    }
    
    /**
     * Teste se os dados jogados possuem o mesmo valor, caso seja verdeiro: true, falso: false.
     * @return Cor do jogador.
     */
    public boolean verificaDadosIguais()
    {
        int valorDado[] = new int[2];                
        for (int i = 0; i <=1; i++)
        {
            valorDado[i] = dados[i].getValor();
        }
        
        if(valorDado[0] == valorDado[1]){
            return true;
        }
        else{
           return false; 
        }
        
    }
}
