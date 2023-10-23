package br.com.TccAndroid.model;

public class Brecho {

    private int id;
    private String titulo;
    private String descricao;
    private String telefone;
    private String horaFuncionaentoDas;
    private String horaFuncionaentoAs;
    private String endereco;
    private String numero;

    private float rating;
    private byte[] fotos;
    private int favorito;
    private int codUsuario;



    public Brecho(int id, String titulo, String endereco,String horaFuncionaentoDas, String horaFuncionaentoAs, byte[] bytes, String numero) {
        this.id = id;
        this.titulo = titulo;
        this.endereco = endereco;
        this.horaFuncionaentoDas = horaFuncionaentoDas;
        this.horaFuncionaentoAs = horaFuncionaentoAs;
        this.fotos = bytes;
        this.numero = numero;
    }


        public Brecho(String titulo, String descricao, String telefone, String horaFuncionaentoDas, String horaFuncionaentoAs, String endereco, String numero, byte[] fotos, int codUsuario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.telefone = telefone;
        this.horaFuncionaentoDas = horaFuncionaentoDas;
        this.horaFuncionaentoAs = horaFuncionaentoAs;
        this.endereco = endereco;
        this.numero = numero;
        this.fotos = fotos;
        this.codUsuario = codUsuario;
    }

    public Brecho(String titulo, String endereco, String horaFuncionaentoDas, String horaFuncionaentoAs, String descricao, String telefone, String string, byte[] fotos, String numero) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.telefone = telefone;
        this.horaFuncionaentoDas = horaFuncionaentoDas;
        this.horaFuncionaentoAs = horaFuncionaentoAs;
        this.endereco = endereco;
        this.numero = numero;
        this.fotos = fotos;
    }

    public Brecho(int anInt, String string, String string1, String string2, String string3, String string4) {
    }

    public Brecho(byte[] fotos) {
        this.fotos = fotos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getHoraFuncionaentoDas() {
        return horaFuncionaentoDas;
    }

    public void setHoraFuncionaentoDas(String horaFuncionaentoDas) {
        this.horaFuncionaentoDas = horaFuncionaentoDas;
    }

    public String getHoraFuncionaentoAs() {
        return horaFuncionaentoAs;
    }

    public void setHoraFuncionaentoAs(String horaFuncionaentoAs) {
        this.horaFuncionaentoAs = horaFuncionaentoAs;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public byte[] getFotos() {
        return fotos;
    }

    public void setFotos(byte[] fotos) {
        this.fotos = fotos;
    }

    public int getFavorito() {
        return favorito;
    }

    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}