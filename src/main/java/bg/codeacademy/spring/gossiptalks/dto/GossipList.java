package bg.codeacademy.spring.gossiptalks.dto;

import com.sun.istack.NotNull;
import java.util.List;
import javax.validation.constraints.Min;

public class GossipList {

  @Min(value = 0)
  @NotNull
  private int pageNumber;
  @NotNull
  private int pageSize;
  @NotNull
  private int count;
  @NotNull
  private int total;
  private List<GossipDto> content;


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

  public List<GossipDto> getContent() {
    return content;
  }

  public GossipList setContent(List<GossipDto> content) {
    this.content = content;
    return this;
  }
}
