package practica2;

public class Mapa {
    private static int filas;
    private static int columnas;
    private static int filaMeta;
    private static int columnaMeta;
    private static int[][] mapa;
    

    public Mapa(String nombre_mapa,int filMeta, int colMeta){
        /* String direccion_mapas = "./maps/" + nombre_mapa;
        
        if(PosionMeta(filMeta, colMeta)) {
            filaMeta = filMeta - 1;
            columnaMeta = colMeta - 1;
        } else {
            System.out.println("Posición de la meta inválida");
        }
   
        try(FileReader fr = new FileReader(direccion_mapas)) {
            BufferedReader br = new  BufferedReader(fr);
            
            Filas = Integer.parseInt(br.readLine());
            Columnas = Integer.parseInt(br.readLine());

            mapa = new int[filas][columnas];
            String linea;

            int fila = 0;

            while((linea=br.readLine())!=null){

                int [] numeros = separar_lineas(linea);

                for(int i = 0; i < Columnas; i++){

                    Mmapaapa[fila][i] = numeros[i];

                }
                fila++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        } */
    }

    public int[][] getMapa(){
        return mapa;
    }

    public boolean PosicionMeta(int fila, int columna){

/*         if(fila>0 && fila<=Filas && columna>0 && columna<=Columnas &&
            Mapa[fila-1][columna-1]==0){
            return true;
        }
        else{
            return false;
        } */return false;

    }

    public int filaMeta(){
        return filaMeta;
    }

    public int columnaMeta(){
        return columnaMeta;
    }

    public int getFilas(){
        return filas;
    }

    public int getColumnas(){
        return columnas;
    }

    public int[] separar_lineas(String lineas){

        String [] num = lineas.split("\\s+");
        int [] numeros = new int [num.length];
        
        for(int i=0;i<num.length;i++){

            numeros[i]=Integer.parseInt(num[i]);

        }

        return numeros;
    }
    
    public void imprimirMapa() {

/*         for (int i = 0; i < Filas; i++) {
            for (int j = 0; j < Columnas; j++) {
                System.out.print(Mapa[i][j] + "     ");
            }
            System.out.println(); 
        } */
    }
    
    public static void main(String[] args) {
/*         
        Mapa mapa = new Mapa();
        mapa.LeerMapa();
        mapa.imprimirMapa(); */
    }
    
}