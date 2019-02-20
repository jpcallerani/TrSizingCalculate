package Control;

import java.util.Date;

public class Arquivo
  implements Comparable<Arquivo>
{
  private String nome;
  private Date data;

  public String getNome()
  {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Date getData() {
    return this.data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public int compareTo(Arquivo o)
  {
    return o.getData().compareTo(getData());
  }
}