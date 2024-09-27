package co.edu.uniquindio.notas.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NotaPrincipal {

    private final List<Nota> notas;

    public NotaPrincipal() {
        notas = new ArrayList<>();
    }

    /**
     * Agregar una nota a la lista de notas
     * @param titulo Titulo de la nota
     * @param descripcion Descripción de la nota
     * @param categoria Categoría de la nota
     * @param recordatorio Fecha de recordatorio de la nota
     * @throws Exception Si alguno de los campos está vacío o la fecha de recordatorio es menor a la fecha actual
     */
    public void agregarNota(String titulo, String descripcion, String categoria, LocalDateTime recordatorio) throws Exception {

        if(titulo.isEmpty() || descripcion.isEmpty() || categoria.isEmpty())
            throw new Exception("Todos los campos son obligatorios");

        if(recordatorio.isBefore(LocalDateTime.now()))
            throw new Exception("La fecha de recordatorio no puede ser menor a la fecha actual");

        Nota nota = Nota.builder()
                .id( UUID.randomUUID().toString() ) //Genera un id aleatorio
                .titulo(titulo)
                .descripcion(descripcion)
                .categoria(categoria)
                .recordatorio(recordatorio)
                .fechaCreacion(LocalDateTime.now()).build();

        notas.add(nota);
    }

    /**
     * Elimina una nota de la lista de notas
     * @param id Id de la nota a eliminar
     * @throws Exception Si no existe la nota con el id proporcionado
     */
    public void eliminarNota(String id) throws Exception{
        int posNota = obtenerNota(id);

        if(posNota == -1){
            throw new Exception("No existe el id proporcionado");
        }

        notas.remove( notas.get(posNota) );
    }

    /**
     * Actualiza una nota de la lista de notas
     * @param id Id de la nota a actualizar
     * @param titulo Titulo de la nota
     * @param descripcion Descripción de la nota
     * @param categoria Categoría de la nota
     * @throws Exception Si no existe la nota con el id proporcionado
     */
    public void actualizarNota(String id, String titulo, String descripcion, String categoria) throws Exception{
        int posNota = obtenerNota(id);

        if(posNota == -1){
            throw new Exception("No existe el id proporcionado");
        }

        Nota notaGuardada = notas.get(posNota);
        notaGuardada.setTitulo(titulo);
        notaGuardada.setCategoria(categoria);
        notaGuardada.setDescripcion(descripcion);

        notas.set(posNota, notaGuardada);
    }

    /**
     * Obtiene la posición de una nota en la lista de notas
     * @param id Id de la nota a buscar
     * @return Posición de la nota en la lista
     */
    public int obtenerNota(String id){

        for (int i = 0; i < notas.size(); i++) {
            if( notas.get(i).getId().equals(id) ){
                return i;
            }
        }

        return -1;
    }

    /**
     * Lista las categorías disponibles
     * @return Lista de categorías
     */
    public ArrayList<String> listarCategorias() {
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("Personal");
        categorias.add("Trabajo");
        categorias.add("Estudio");
        categorias.add("Otro");

        return categorias;
    }

    public List<Nota> listarNotas() {
        return notas;
    }

}
