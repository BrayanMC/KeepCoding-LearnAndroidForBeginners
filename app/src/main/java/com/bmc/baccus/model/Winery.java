package com.bmc.baccus.model;

import com.bmc.baccus.R;

import java.util.Arrays;
import java.util.List;

public class Winery {

    private static Winery sInstace = null;

    private List<Wine> listWines = null;

    private synchronized static void createInstance() {
        if (sInstace == null) {
            sInstace = new Winery();
        }
    }

    public static Winery getInstance() {
        if (sInstace == null) createInstance();
        return sInstace;
    }

    private Wine createWinesObject_Bembibre() {
        Wine bembibre = new Wine(
                "Bembibre",
                "Tinto",
                R.drawable.bembibre,
                "Dominio de Tares",
                "http://www.dominiodetares.com/portfolio/bembibre/",
                "Este vino muestra toda la complejidad y la elegancia de la variedad Mencía. En fase visual luce un color rojo picota muy cubierto con tonalidades violáceas en el menisco. En nariz aparecen recuerdos frutales muy intensos de frutas rojas (frambuesa, cereza) y una potente ciruela negra, así como tonos florales de la gama de las rosas y violetas, vegetales muy elegantes y complementarios, hojarasca verde, tabaco y maderas aromáticas (sándalo) que le brindan un toque ciertamente perfumado.",
                "El Bierzo",
                5);
        bembibre.addGrape("Mencía");

        return bembibre;
    }

    public Winery() {
        listWines = Arrays.asList(createWinesObject_Bembibre(), createWinesObject_Vegaval(), createWinesObject_Zarate(), createWinesObject_Champagne());
    }

    private Wine createWinesObject_Vegaval() {
        Wine vegaval = new Wine(
                "Vegaval",
                "Tinto",
                R.drawable.vegaval,
                "Miguel Calatayud",
                "http://www.vegaval.com/es",
                "Un vino de esmerado proceso de elaboración y larga crianza. Presenta un color rojo cereza con matices a teja y una brillante capa media alta. Nariz compleja, fina y elegante. Es excelentemente estructurado, amplio y muy sabroso. Recomendado para acompañar quesos curados, estofados y todo tipo de carnes rojas y de caza. La temperatura recomendada para servir está entre los 16º C y 18º C.",
                "Valdepeñas",
                5);
        vegaval.addGrape("Tempranillo");

        return vegaval;
    }

    private Wine createWinesObject_Zarate() {
        Wine zarate = new Wine(
                "Zárate",
                "Blanco",
                R.drawable.zarate,
                "Miguel Calatayud",
                "http://bodegas-zarate.com/productos/vinos/albarino-zarate/",
                "El albariño Zarate es un vino blanco monovarietal que pertenece a la Denominación de Origen Rías Baixas. Considerado por la crítica especializada como uno de los grandes vinos blancos del mundo, el albariño ya es todo un mito.",
                "Rías bajas",
                4);
        zarate.addGrape("Albariño");

        return zarate;
    }

    private Wine createWinesObject_Champagne() {
        Wine champagne = new Wine(
                "Champagne",
                "Otros",
                R.drawable.champagne,
                "Champagne Taittinger",
                "http://bodegas-zarate.com/productos/vinos/albarino-zarate/",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ac nunc purus. Curabitur eu velit mauris. Curabitur magna nisi, ullamcorper ac bibendum ac, laoreet et justo. Praesent vitae tortor quis diam luctus condimentum. Suspendisse potenti. In magna elit, interdum sit amet facilisis dictum, bibendum nec libero. Maecenas pellentesque posuere vehicula. Vivamus eget nisl urna, quis egestas sem. Vivamus at venenatis quam. Sed eu nulla a orci fringilla pulvinar ut eu diam. Morbi nibh nibh, bibendum at laoreet egestas, scelerisque et nisi. Donec ligula quam, semper nec bibendum in, semper eget dolor. In hac habitasse platea dictumst. Maecenas adipiscing semper rutrum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;",
                "Comtes de Champagne",
                5);
        champagne.addGrape("Chardonnay");

        return champagne;
    }

    public Wine getWine(int index) {
        return listWines.get(index);
    }

    public int getWineCount() {
        return listWines.size();
    }
}
