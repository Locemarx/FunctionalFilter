import java.util.stream.Stream

typealias Filter = (String) -> ((Map.Entry<TargetKey, String>) -> Boolean)

class TargetFilterHandler {

    private val identity: (Map.Entry<TargetKey, String>) -> Boolean = { entry -> true }

    private val nomeFilter: Filter = { filtro ->
        { entry ->
            (entry.key.type() == TargetKey.Type.A
                    || entry.key.type() == TargetKey.Type.C)
                    && entry.value.contains("nome=" + filtro)
        }
    }

    private val destinazioneFilter: Filter = { filtro ->
        { entry ->
            entry.key.type() == TargetKey.Type.A
                    && entry.value.contains("destinazione=" + filtro)
        }
    }

    private fun and(
        x: (Map.Entry<TargetKey, String>) -> Boolean,
        y: (Map.Entry<TargetKey, String>) -> Boolean
    ): (Map.Entry<TargetKey, String>) -> Boolean {
        return { i -> x(i) && y(i) }
    }


    private val mapFilter = mapOf<String, Filter>("nome" to nomeFilter, "destinazione" to destinazioneFilter)

    fun applyFilters(parameters: Map<String, List<String>>)
            : (Map.Entry<TargetKey, String>) -> Boolean {

        return parameters.entries.stream()

            .map {
                val filter = mapFilter[it.key]
                if (filter == null) {
                    println("Not found filter ${it.key}")
                    identity
                } else filter(it.value[0])
            }.reduce(identity, { x, y -> { i -> x(i) && y(i) } })
            //.reduce(identity, {x,y -> and(x,y)})
    }
}

