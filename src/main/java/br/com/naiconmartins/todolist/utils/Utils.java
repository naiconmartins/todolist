package br.com.naiconmartins.todolist.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    /**
     * Este método copia as propriedades (atributos) de um objeto para outro,
     * mas **ignora** os campos que são nulos no objeto de origem (source).
     *
     * Exemplo prático:
     * Se você quiser atualizar um TaskModel, e o usuário só mandou "title" e "description",
     * este método copia apenas esses dois campos, sem sobrescrever os outros com `null`.
     *
     * @param source objeto de origem (de onde vêm os dados)
     * @param target objeto de destino (para onde os dados serão copiados)
     */
    public static void copyNonNullProperties(Object source, Object target) {
        // BeanUtils.copyProperties é uma função do Spring que copia valores entre objetos do mesmo tipo
        // O terceiro parâmetro é uma lista de propriedades que NÃO devem ser copiadas
        // Aqui, passamos os nomes das propriedades que são nulas no 'source', para que elas sejam ignoradas
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * Este método devolve uma lista (array) com os nomes dos atributos
     * que estão nulos no objeto recebido.
     *
     * Ele é usado acima para saber quais propriedades devem ser ignoradas
     * durante a cópia.
     *
     * @param source objeto que será analisado
     * @return um array de Strings contendo o nome dos atributos nulos
     */
    public static String[] getNullPropertyNames(Object source) {
        // BeanWrapper é uma classe do Spring que permite acessar as propriedades de um objeto dinamicamente
        // Exemplo: ler e escrever valores sem precisar de getters/setters diretamente
        final BeanWrapper src = new BeanWrapperImpl(source);

        // PropertyDescriptor fornece informações sobre as propriedades do objeto
        // (como nome, tipo, métodos getter e setter)
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        // Aqui armazenaremos os nomes dos atributos que estão nulos
        Set<String> emptyNames = new HashSet<>();

        // Percorre todas as propriedades do objeto
        for (PropertyDescriptor pd : pds) {
            // Pega o valor atual da propriedade (chama o getter internamente)
            Object srcValue = src.getPropertyValue(pd.getName());

            // Se o valor for nulo, adiciona o nome dessa propriedade ao conjunto
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        // Converte o Set em um array de Strings, pois o BeanUtils.copyProperties precisa de um array
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
