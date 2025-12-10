package edu.msmk.clases;
import edu.msmk.clases.exchange.PeticionCliente;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/// // Planteamos las zonas que podemos dar covertura según la siguiente estructura:
/// // Provincia -> Municipio -> Unidad poblacional -> Vía
public class CoberturaServicio {
    /// // Estructura: Map<Provincia, Map<Municipio, Map<U.P., HashSet<Vía>>>>
    private final Map<Integer, Map<Integer, Map<Integer, HashSet<Integer>>>> cobertura;

    /// // OPERACIONES COBERTURA SERVICIO
    public CoberturaServicio() {
        this.cobertura = new HashMap<>();
    }

    ///  // Añadimos un tramo a la estructura jerárquica
    public void addTramo(Integer provincia, Integer municipio, Integer unidadPoblacional, Integer via) {

        /// // 1. Obtener o crear el mapa de Municipios (y el resto de la jerarquía)
        Map<Integer, Map<Integer, HashSet<Integer>>> municipiosYResto =
                this.cobertura.computeIfAbsent(provincia, k -> new HashMap<>());

        /// // 2. Obtener o crear el mapa de Unidades Poblacionales (y el resto)
        Map<Integer, HashSet<Integer>> unidades =
                municipiosYResto.computeIfAbsent(municipio, k -> new HashMap<>());

        /// // 3. Obtener o crear el conjunto de Vías
        HashSet<Integer> vias =
                unidades.computeIfAbsent(unidadPoblacional, k -> new HashSet<>());

        /// // 4. Agregar la Vía al conjunto
        vias.add(via);
    }

    /// // Añadimos una funcionalidad de N.º Provincias Cubiertas
    public int numeroProvinciasCubiertas(){
        return this.cobertura.size();
    }

    public boolean damosServicio(PeticionCliente direccion){
        /// // Validación jerárquica: Debe existir en todos los niveles

        /// // 1. Comprobar Provincia
        // Renombrado para mayor claridad (contiene Municipios Y el resto de la estructura)
        Map<Integer, Map<Integer, HashSet<Integer>>> municipiosYResto =
                cobertura.get(direccion.getProvincia());
        if (municipiosYResto == null) return false;

        /// // 2. Comprobar Municipio
        Map<Integer, HashSet<Integer>> unidades =
                municipiosYResto.get(direccion.getMunicipio());
        if (unidades == null) return false;

        /// // 3. Comprobar Unidad Poblacional
        HashSet<Integer> vias =
                unidades.get(direccion.getUnidadPoblacional());
        if (vias == null) return false;

        /// // 4. Comprobar Vía
        return vias.contains(direccion.getVia());
        // NO es necesario comprobar el número de vía ya que el archivo TRAM sólo contiene los tramos, no los números exactos.
    }
}