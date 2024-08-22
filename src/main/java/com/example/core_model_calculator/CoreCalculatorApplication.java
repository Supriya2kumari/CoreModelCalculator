package com.example.core_model_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CoreCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreCalculatorApplication.class, args);
    }
}

@RestController
class CoreModelController {

    private static final Map<String, CoreModel> coreModelData = new HashMap<>();

    static {
        coreModelData.put("EE25/13/7", new CoreModel("EE25/13/7", 5, 5, 7, 7, 5.0));
        coreModelData.put("EE16/8/5", new CoreModel("EE16/8/5", 4, 4, 6, 6, 4.0));
    }

    @GetMapping("/calculate")
    public Map<String, Object> calculate(@RequestParam String coreModel) {
        CoreModel model = coreModelData.get(coreModel);

        if (model == null) {
            throw new IllegalArgumentException("Invalid core model: " + coreModel);
        }

        double effectiveArea = model.getCenterLegWidth() * model.getCenterLegThickness();
        double coreWindowArea = model.getWindowWidth() * model.getWindowHeight();
        double effectiveVolume = effectiveArea * model.getEffectiveLength();
        double coreFactor = (effectiveArea * model.getEffectiveLength()) / effectiveVolume;

        Map<String, Object> results = new HashMap<>();
        results.put("coreFactor", coreFactor);
        results.put("effectiveVolume", effectiveVolume);
        results.put("effectiveLength", model.getEffectiveLength());
        results.put("effectiveArea", effectiveArea);
        results.put("coreWindowArea", coreWindowArea);

        return results;
    }
}

class CoreModel {
    private String name;
    private double centerLegWidth;
    private double centerLegThickness;
    private double windowWidth;
    private double windowHeight;
    private double effectiveLength;

    public CoreModel(String name, double centerLegWidth, double centerLegThickness, double windowWidth,
            double windowHeight, double effectiveLength) {
        this.name = name;
        this.centerLegWidth = centerLegWidth;
        this.centerLegThickness = centerLegThickness;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.effectiveLength = effectiveLength;
    }

    public double getCenterLegWidth() {
        return centerLegWidth;
    }

    public double getCenterLegThickness() {
        return centerLegThickness;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public double getEffectiveLength() {
        return effectiveLength;
    }
}