package bg.codeacademy.spring.gossiptalks.dto;

import java.util.ArrayList;

public class GossipList {

  private int pageNumber;
  private int pageSize;
  private int count;
  private int total;
  private ArrayList<String> content;

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

  public ArrayList<String> getContent() {
    return content;
  }

  public GossipList setContent(ArrayList<String> content) {
    this.content = content;
    return this;
  }
}
