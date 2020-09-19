package com.ecs.utils;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhaoone on 2019/11/13
 **/
public class RiskAssessmentUtil {
    public static String predictLrHeart( double[] array,String  pathxml)throws Exception {

        PMML pmml = new PMML();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(pathxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(inputStream == null){
        }
        InputStream is = inputStream;
        try {
            pmml = org.jpmml.model.PMMLUtil.unmarshal(is);
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (JAXBException e1) {
            e1.printStackTrace();
        }finally {
            //关闭输入流
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
        Evaluator evaluator = modelEvaluatorFactory.newModelEvaluator(pmml);
        pmml = null;
//        Map<String, ?> result = evaluator.evaluate(map);
//        Set<String> keySet = result.keySet();  //获取结果的keySet
//        for(String fn : keySet){
//            System.out.println(result.get(fn).toString());
//        }
        List<InputField> inputFields = evaluator.getInputFields();
        // 过模型的原始特征，从画像中获取数据，作为模型输入
        Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();

        int index = 0;
        for (InputField inputField : inputFields) {
            FieldName inputFieldName = inputField.getName();

            FieldValue inputFieldValue = inputField.prepare((Object)array[index]);
            arguments.put(inputFieldName, inputFieldValue);
            index++;
        }
        System.out.println(arguments);
        Map<FieldName, ?> results = evaluator.evaluate(arguments);
        List<TargetField> targetFields = evaluator.getTargetFields();
        //对于分类问题等有多个输出。
        for (TargetField targetField : targetFields) {
            FieldName targetFieldName = targetField.getName();
            Object targetFieldValue = results.get(targetFieldName);

//            0.076
            String str[] = targetFieldValue.toString().split("result=");
            String riskLevel = str[1].charAt(0)+"";

            return riskLevel;//正則轉成等級
        }
        return "";
    }
}
