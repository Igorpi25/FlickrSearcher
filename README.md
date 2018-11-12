Flickr Searcher Demo
===========


Architecture Components
---
- ViewModel,LiveData вспомогательные библиотеки для реализации MVVM-паттерна
- Paging Library используется для постраничной загрузки изображений из сервера
- RecyclerView, CardView

External Frameworks & Libs
----

- [Toothpick][1] DI фреймворк
- [Moshi][2] библиотека для работы с JSON
- Retrofit фрейсворк для работы с REST-сервером 
- Butterknife библиотека для биндинга layout файлов
- Picasso библиотека для биндинга изображений из сервера на ImageView
- RxJava используется для упрощения синтаксиса работы с Retrofit

Дополнительная информация об используемых библиотеках
----
- [Toothpick][3] - управление зависимостями в Android приложениях. Youtube видео с DevFestSiberia2017
- [Advanced JSON parsing][4] techniques using Moshi and Kotlin

License
-------

See the [LICENSE](LICENSE) file for license rights and limitations (Apache).

[1]:https://github.com/stephanenicolas/toothpick
[2]:https://github.com/square/moshi
[3]:https://www.youtube.com/watch?v=EOFrA-MHbjU
[4]:https://medium.com/@BladeCoder/advanced-json-parsing-techniques-using-moshi-and-kotlin-daf56a7b963d
