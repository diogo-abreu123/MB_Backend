package MBPackage;


public class Vehicle {
    private String id;
    private String model;
    private String fuel;
    private String transmission;

    public Vehicle(String id, String model, String fuel, String transmission)
    {
        this.id = id;
        this.model = model;
        this.fuel = fuel;
        this.transmission = transmission;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getFuel()
    {
        return fuel;
    }

    public void setFuel(String fuel)
    {
        this.fuel = fuel;
    }

    public String getTransmission()
    {
        return transmission;
    }

    public void setTransmission(String transmission)
    {
        this.transmission = transmission;
    }
}
