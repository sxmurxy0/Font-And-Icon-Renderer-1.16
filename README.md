<h2 align="center">Font-And-Icon-Renderer-1.16:bulb:</h2>

### ENG
With this code, you can render custom fonts `ttf` and `otf` in your minecraft projects. </br>
There are three renderers: **IconRenderer**, **StyledRenderer** and **SimplifiedFontRenderer**.
* **IconRenderer** - for rendering icon fonts. In the IconFont class constructor you specify characters, whose icons you are going to draw.
```java
 public IconFont(String fileName, int size, char... chars)
```
* **StyledRenderer** - for rendering fonts with the ability to use minecraft text styles, wich are determited by `§` character.
* **SimplifiedFontRenderer** - simplified font renderer without support minecraft styles. </br></br>

**Font settings:**:small_orange_diamond:
* use `lifting` parameter to raise or lower the text
* use `spacing` parameter to adjust the distance between letters
* use `stretching` parameter to adjust the width of the letters

**Each of these parameters can be a negative value.**

### RU
Это готовая утилита для рендера `ttf` и `otf` шрифтов на экране в ваших проектах. </br>
В репозитории есть 3 различных renderer'а: **IconRenderer**, **StyledRenderer** и **SimplifiedFontRenderer**. 
* **IconRenderer** - для рендера шрифтов с иконками. В конструкторе IconFont необходимо передать символы, соответственные иконки которых вы собираетесь отрисовывать.
```java
 public IconFont(String fileName, int size, char... chars)
```
* **StyledRenderer** - для рендера шрифтов с возможностью использоавния стилей майнкрафта, которые указываются с помощью символа `§`.
* **SimplifiedFontRenderer** - упрощенный рендерер шрифтов без поддержки стилей майнкрафта. </br></br>

**StyledFont и TextFont имеют некоторые настройки:**:small_orange_diamond:
* Вы можете указать язык через enum `Lang`. Если вы указываете *`ENG_RU`*, то важно, чтобы шрифт, который вы используете, поддерживал русский язык.
* С помощью параметра `lifting` регулируется поднятие шрифта.
* С помощью параметра `spacing` регулируется отступ между символами.
* С помощью параметра `stretching` регулируется растяжение символов. Чем он больше - тем сильнее каждый символ будет растянут в ширину.

**Каждый из этих параметров может быть отрицательным значением.**



## Showcase:purple_heart::
![2023-05-05_07 28 12](https://user-images.githubusercontent.com/46312126/236338369-b920991a-51d6-4e3e-bc20-9dd79e361269.png)
![2023-02-08_19 48 38](https://user-images.githubusercontent.com/46312126/217508159-01923de6-31f0-4bbf-adeb-8fe0d10b19a0.png)
![2023-02-08_19 48 56](https://user-images.githubusercontent.com/46312126/217508268-942874a0-60f4-4927-9db4-8c7a09e0363a.png)
