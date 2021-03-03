package bg.codeacademy.spring.gossiptalks.dto;

import bg.codeacademy.spring.gossiptalks.model.Gossip;
import com.sun.istack.NotNull;
import java.util.ArrayList;

public class GossipList {

  // min = 0
  @NotNull
  private int pageNumber;

  @NotNull
  private int pageSize;

  @NotNull
  private int count;

  @NotNull
  private int total;

  private ArrayList<Gossip> content;

  public int getPageNumber() {
    return pageNumber;
  }

  public GossipList setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
    return this;
  }

  public int getPageSize() {
    return pageSize;
  }

  public GossipList setPageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public int getCount() {
    return count;
  }

  public GossipList setCount(int count) {
    this.count = count;
    return this;
  }

  public int getTotal() {
    return total;
  }

  public GossipList setTotal(int total) {
    this.total = total;
    return this;
  }

  public ArrayList<Gossip> getContent() {
    return content;
  }

  public GossipList setContent(ArrayList<Gossip> content) {
    this.content = content;
    return this;
  }
}
