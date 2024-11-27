package conexion;

import resources.XmlTransformer;

public abstract class Conector {

    public Puerto puerto;
    public String fichero;
    
    public XmlTransformer transformer = new XmlTransformer();
    
    public Conector(Puerto puerto, String fichero) {
        this.puerto = puerto;
        this.fichero = fichero;
    }

    public Conector(Puerto puerto) {
        this.puerto = puerto;

    }

    public String getFichero() {
        return fichero;
    }

    public Puerto getPuerto() {
        return puerto;
    }

    public void setPuerto(Puerto puerto) {
        this.puerto = puerto;
    }
}
