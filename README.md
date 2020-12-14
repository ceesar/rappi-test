# rappi-test

App realizada con la arquitectura MVVM:

<img align="bottom" src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png" width="200">

Utilizando los principales componentes de Jetpack architecture y kotlin:

 * ViewModel
 * LiveData
 * Hilt (for dependency injection)
 * Kotlin Flows
 * Retrofit
 * Navigation
 * Paging
 * Room
 * View Binding
 * Data Bingind
 
Al momento de desarrollar este challenge me enfoque principalmente en la funcionalidad offline para que quedara lo mejor posible para el usuario.
Tenía pensado implementar un componente lo más genérico posible para poder guardar todas las películas en una sola tabla y en otra tabla guardar el orden de la clasificación correspondiente pero por cuestiones de tiempo ya no alcance a implementarlo.


De todos los puntos a realizar en el challenge únicamente me faltaron 2 por falta de tiempo, que fueron las pruebas unitarias y la reproducción de videos.

•	Pruebas unitarias: Me apoyaría con las principales herramientas como Mockito, Espresso.

•	Reproducción de videos: Para la reproducción de videos me hubiera apoyado con las herramientas nativas de Android(VideoView y MediaController) ya que es fácil la implementación y configuración. Y en dado caso de tener que realizar algo más complejo utilizaría la librería de ExoPlayer ya que te permite una mayor configuración.
