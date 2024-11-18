package Tasks;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import conexion.Slot;
import java.util.ArrayList;
import resources.XmlTransformer;

public abstract class Task {

    private XmlTransformer procesador;
    
    private ArrayList<Slot> input, output;

    public Task() {
    }

    public Task(ArrayList<Slot> input, ArrayList<Slot> output) {
        this.input = new ArrayList<>();
        this.output = new ArrayList<>();
        this.procesador = new XmlTransformer();
        this.input = input;
        this.output = output;
    }

    public XmlTransformer getXmlTransformer(){
        return procesador;
    }
    
    public ArrayList<Slot> getInput() {
        return input;
    }

    public void setInput(ArrayList<Slot> input) {
        this.input = input;
    }

    public ArrayList<Slot> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<Slot> output) {
        this.output = output;
    }

    public void run() throws IllegalArgumentException, ParserConfigurationException, Exception{
        
    }

}
