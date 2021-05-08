

public enum MatchState
{
    TOSTART, FIRSTHALF, SECONDHALF, INTERVALL, FINISHED;
    
    public String toString(){
       switch(this){
           case TOSTART: return "Por Iniciar";
           case FIRSTHALF: return "Primeira Parte";
           case SECONDHALF: return "Segunda Parte";
           case INTERVALL: return "Intervalo";
           case FINISHED: return "Terminado";
           default: return "Indefenido";
        }
    }

}
