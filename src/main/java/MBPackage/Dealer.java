package MBPackage;


import java.util.List;

public class Dealer {

    private String id;
    private String name;
    private Long latitude;
    private Long longitude;
    private List<String> closed;

    public Dealer(String id, String name, Long latitude, Long longitude, List<String> closed) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.closed = closed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Long latitude)
    {
        this.latitude = latitude;
    }

    public Long getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Long longitude)
    {
        this.longitude = longitude;
    }

    public List<String> getClosed()
    {
        return closed;
    }

    public void setClosed(List<String> closed)
    {
        this.closed = closed;
    }

}
