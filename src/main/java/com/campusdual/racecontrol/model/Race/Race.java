package com.campusdual.racecontrol.model.Race;

import com.campusdual.racecontrol.model.ScoreCar;
import com.campusdual.racecontrol.model.Garage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class Race {
    protected static final String RACE_TAG = "Races";
    protected static final String RACE_ID = "id";
    protected static final String RACE_NAME = "name";
    protected static final String RACE_TYPE = "type";
    protected static final String STANDARD_TYPE = "type";
    protected static final String ELIMINATION_TYPE = "elimination";
    protected static final String RACE_S_DURATION = "duration";
    protected static final String RACE_E_WARM = "warm-up time";
    protected static final String RACE_GARAGES = "garages";
    protected static final String RACE_CARS = "cars";
    private static long idCount = 0;

    protected long id;
    protected String name;
    protected boolean finished = false;
    protected List<Garage> garages = new ArrayList<>();
    protected List<ScoreCar> cars = new ArrayList<>();

    protected ScoreCar[] podium = new ScoreCar[3];

    public Race(String name) {
        this.name = name;
        this.id = generateId();
    }

    protected static long generateId(){
        return Race.idCount++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }

    public List<ScoreCar> getCars() {
        return cars;
    }

    public void setCars(List<ScoreCar> cars) {
        this.cars = cars;
    }

    public ScoreCar[] getPodium() {
        return podium;
    }

    public String checkPodium(){
        if (isFinished()){
            StringBuilder sb = new StringBuilder();
            sb.append("1# ");
            sb.append(podium[0]);
            sb.append("#2 ");
            sb.append(podium[1]);
            sb.append("3#" );
            sb.append(podium[2]);
            return sb.toString();
        } else {
            return "Race hasn't happened yet.";
        }
    }

    public void setPodium(ScoreCar[] podium) {
        this.podium = podium;
    }

    public abstract void startRace();

    public static Race importRace(JSONObject object){
        long id = (long)object.get(RACE_ID);
        String name = (String)object.get(RACE_NAME);
        String type =  (String)object.get(RACE_TYPE);

        Race race;

        if (type.equals("Standard")){
            int duration = (int)object.get(RACE_S_DURATION);
            race = new StandardRace(name, duration);
        } else if (type.equals("Elimination")){
            race = new EliminationRace(name);
        } else {
            return null;
        }

        race.setId(id);

        return race;
    }
    public abstract JSONObject exportRace();
    public  static JSONObject importJSONFile(String filename){
        /*TODO*/
        return null;
    }
    public  static void exportJSONArrayToFile(ArrayList<Race> races, String filename){
        JSONArray jsonArray = new JSONArray();
        for (Race r : races){
            jsonArray.add(r.exportRace());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(RACE_TAG, jsonArray);

        try (
                FileWriter fw = new FileWriter(filename)
        ) {
            fw.write(jsonObject.toJSONString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", garages=" + garages +
                ", cars=" + cars +
                '}';
    }

    public static void main(String[] args) {
        Race race1 = new StandardRace("Standard race 1", 180);
        Race race2 = new EliminationRace("Elimination race 1");
        Race race3 = new StandardRace("Standard race 2", 200);

        ArrayList<Race> races = new ArrayList<>();
        races.add(race1);
        races.add(race2);
        races.add(race3);

        Garage garagePaco = new Garage("Garaje Paco");

        ScoreCar car1 = new ScoreCar("Seat", "Ibiza");
        ScoreCar car2 = new ScoreCar("Citroen", "Xsara");
        ScoreCar car3 = new ScoreCar("Opel", "Corsa");

        car1.setGarageName(garagePaco.getName());
        car2.setGarageName(garagePaco.getName());
        car3.setGarageName(garagePaco.getName());

        garagePaco.getCars().add(car1);
        garagePaco.getCars().add(car2);
        garagePaco.getCars().add(car3);

        Garage tallerManolo = new Garage("Taller Manolo");
        ScoreCar car4 = new ScoreCar("Volkswagen", "Polo");
        ScoreCar car5 = new ScoreCar("Volkswagen", "Golf");
        tallerManolo.getCars().add(car4);
        tallerManolo.getCars().add(car5);

        Garage escuderiaLoli = new Garage("Escuderia Loli");
        ScoreCar car6 = new ScoreCar("Ford", "Mustang");
        escuderiaLoli.getCars().add(car6);

        ArrayList<Garage> garages = new ArrayList<>();
        garages.add(garagePaco);
        garages.add(tallerManolo);
        garages.add(escuderiaLoli);

        race1.getGarages().add(garagePaco);
        race2.getGarages().add(escuderiaLoli);
        race2.getGarages().add(tallerManolo);
        race3.getGarages().add(garagePaco);
        race3.getGarages().add(tallerManolo);
        race3.getGarages().add(escuderiaLoli);

        exportJSONArrayToFile(races, "races.json");

    }
}
