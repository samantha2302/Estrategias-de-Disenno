# Estrategias de Diseño

Instituto Tecnológico de Costa Rica.<br/>
II Proyecto de Analisis de Algoritmos, primer semestre, 2022.<br/>
- Samantha Acuña Montero.
- Fernanda Sanabria Solano
- Raquel Arguedas Sánchez.

El diseño de algoritmos es importante para la investigación de operaciones, ya que, al implementarlo en un problema en específico, se puede crear un modelo ajustado al problema propuesto.

## Programación dinámica
La programación dinámica busca la solución a un problema creando subproblemas más pequeños con el fin de buscar una manera de optimizar por medio de la recursividad. 

### Estructuras utilizadas en el algoritmo dinámico 
Array de enteros. El valor de n corresponde a la cantidad de presentaciones (kg) utilizadas para el algoritmo.
<p align='center'>
<img src="https://res.cloudinary.com/dgm059qwp/image/upload/v1655348658/Analisis%20de%20Algoritmos%20/II%20project/Imagen2_jwtmgg.png" width="250"
</p>
  
Array de String. El valor de n corresponde a la cantidad de presentaciones (kg) utilizadas para el algoritmo.  El valor de m corresponde a la cantidad de posibles combinaciones entre las presentaciones (presentación 1 y 2, presentación 1 y 3,...).
<p align='center'>
<img src="https://res.cloudinary.com/dgm059qwp/image/upload/v1655348658/Analisis%20de%20Algoritmos%20/II%20project/Imagen3_tbce5z.png" width="500"
</p>
  
ArrayList de ArrayList de Enteros. El valor de n corresponde a la cantidad de presentaciones (kg) utilizadas para el algoritmo.  El valor de m corresponde a la cantidad de posibles combinaciones entre las presentaciones (presentación 1 y 2, presentación 1 y 3,...).
<p align='center'>
<img src="https://res.cloudinary.com/dgm059qwp/image/upload/v1655348658/Analisis%20de%20Algoritmos%20/II%20project/Imagen4_azre1d.png" width="500"
</p>
  
## Algoritmos Genéticos
Los algoritmos genéticos son un método de búsqueda y optimización, el cual su estrategia está basada en los procesos evolutivos de los organismos vivos. 
### Estruturas utilizadas en el algoritmo genético
Array de enteros. El valor de n corresponde a la cantidad de presentaciones (kg) utilizadas para el algoritmo.
<p align='center'>
<img src="https://res.cloudinary.com/dgm059qwp/image/upload/v1655348658/Analisis%20de%20Algoritmos%20/II%20project/Imagen2_jwtmgg.png" width="250"
</p>

Array de array de enteros.  El valor de n corresponde a la cantidad de presentaciones (kg) utilizadas para el algoritmo. El valor de m corresponde a 2 que son los hijos que salen por cruce. Esta estructura es utilizada en el cruce uniforme.
<p align='center'>
<img src="https://res.cloudinary.com/dgm059qwp/image/upload/v1655348658/Analisis%20de%20Algoritmos%20/II%20project/Imagen6_vq0595.png" width="400"
</p>
  
Array de array de enteros.  El valor de n corresponde a la cantidad de presentaciones (kg) utilizadas para el algoritmo. El valor de m corresponde a la cantidad de población inicial elegida según la cantidad de presentaciones. Esta estructura es utilizada para guardar la población inicial de los cruces uniforme y dos puntos.  
<p align='center'>
<img src="https://res.cloudinary.com/dgm059qwp/image/upload/v1655348658/Analisis%20de%20Algoritmos%20/II%20project/Imagen7_te3cnw.png" width="400"
</p>
  
ArrayList de array de enteros.  El valor de n corresponde a la cantidad de presentaciones (kg) utilizadas para el algoritmo. El valor de m corresponde a la cantidad de población que sigue la siguiente fórmula (cantidad de población inicial + (cantidad de hijos * cantidad de generaciones) ). Esta estructura es utilizada para guardar la población total de los cruces uniforme y dos puntos.
<p align='center'>
<img src="https://res.cloudinary.com/dgm059qwp/image/upload/v1655348658/Analisis%20de%20Algoritmos%20/II%20project/Imagen8_jjpsl8.png" width="400"
</p>
 
### Tipos de Cruces
Para este proyecto se realizaron dos tipos de cruces en el desarrollo del algoritmo genético, estos fueron el cruce uniforme y el cruce dos puntos.

El cruce uniforme se puede realizar de dos formas, la primera es escogiendo un número aleatorio y dependiendo del rango se escoge un padre o el otro, la segunda manera es mediante una máscara que determina, mediante unos y ceros, cuál progenitor corresponde a cuál índice. Para efectos del proyecto escogimos la segunda forma mencionada, utilizamos una máscara.
<p align='center'>
<img src="https://res.cloudinary.com/dgm059qwp/image/upload/v1655355643/Analisis%20de%20Algoritmos%20/II%20project/descargar_op3bto.png" width="400"
</p>
  
El segundo cruce utilizado fue el cruce dos puntos, en este se definen dos puntos, el primer padre se copia desde el inicio hasta el primer punto y desde el segundo punto hasta el final, y el segundo progenitor definía lo que correspondía copiar del primer corte al segundo punto.
<p align='center'>
<img src="https://res.cloudinary.com/dgm059qwp/image/upload/v1655355643/Analisis%20de%20Algoritmos%20/II%20project/Cruce_de_dos_puntos_padre_1_madre_hijo_1_hijo_2_nuxm7c.jpg" width="400"
</p>
  
### Mutación 
La mutación que se aplicó fue si se encontraban dos cromosomas que eran idénticos en las cantidades, se suma 1 al primer elemento, y de la misma forma, revisa la puntuación anterior con la que obtiene luego de la mutación y se comparan ambas puntuaciones y se deja la mejor. 
  
### Función aptitud
Para facilitar el proceso de la función aptitud se tomó la decisión de ingresar a los nuevos individuos de manera ordenada dentro del ArrayList de Arrays de elementos de sus cruces respectivos. Esto permite que la evaluación de todos los individuos sea definida por la ubicación que ocupan en el ArrayList siendo los primeros índices (0, 1, 2, …) aquellos individuos, o combinaciones de presentaciones, que tienen un menor desperdicio. 
Debido a lo anterior, la función aptitud sustituye a los individuos en la población inicial, o los individuos que serán utilizados para el siguiente cruce, con los primeros índices del ArrayList hasta haber sustituido cada uno de los elementos en el Array de población inicial.

## Conclusiones
  - ¿Cuál estrategia de cruce es más eficiente para resolver este problema?<br>
  
Según los resultados de las mejores 5 combinaciones evaluando varias presentaciones podemos concluir que el cruce de dos puntos tiene resultados con un desperdicio menor al cruce uniforme. Por lo anterior, podemos decir que una estrategia que llega a resultados más precisos.
Por otro lado, con respecto a las asignaciones, comparaciones y memoria el cruce dos puntos gasta más recursos que el cruce uniforme. Cuando se utilizan 3, 5 y 6 elementos no hay mucha diferencia entre ambos, pero cuando usamos 9 elementos podemos ver con facilidad cuánto más consume el cruce dos puntos. Viéndolo desde los recursos utilizados podríamos decir que el cruce uniforme es mejor.

  - ¿Conforme crece la talla cuál algoritmo se va haciendo más eficiente?<br>
  
Podemos ver que en el algoritmo genético aun cuando son cifras muy altas no hay grandes brincos de crecimiento entre un valor y otro; por otro lado, el algoritmo dinámico empieza con subiendo no con grandes brincos, pero después al insertar un dato más grande como el 9 crece de gran manera, en especial en los datos de la memoria.
Lo anterior evidencia que aun cuando el genético tiene números muy altos el dinámico también empieza a crecer bastante, se observa mejor en los datos de la memoria por lo que podríamos decir que el algoritmo dinámico es mejor según el crecimiento de la talla, pero aun así empieza a crecer tanto como el genético.
