package rest.JyRESTAPI.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import rest.JyRESTAPI.utils.https.CustomResponseException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TransUtils {

    public static final String DATE_TYPE_REG = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    private static final Logger log = LoggerFactory.getLogger(TransUtils.class);

    public static boolean bigDecimalIsEqual(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.compareTo(b) == 0;
    }

    // TOTALCount 변경 함수
    public static int transGetTotalByList(List<Map<String, Object>> list) {
        return list.isEmpty() ? 0 : Integer.parseInt(list.get(0).get("TOTALCOUNT").toString());
    }

    // 셀렉트 박스 스트링 리스트로 변환하는 함수
    public static List<String> stringToArraySplitComma(Object stringTypeValue) throws CustomResponseException {

        if (stringTypeValue == null)
            return List.of();

        try {
            String stringValue = String.valueOf(stringTypeValue);
            if (!"null".equals(stringValue) && !stringValue.isBlank()) {
                String[] strArr = StringUtils.split(stringValue, ",");
                return Arrays.asList(strArr);
            }
        } catch (Exception e) {
            throw new CustomResponseException("올바르지 않은 변환입니다. stringToArraySplitComma", 500);
        }
        return List.of();
    }

    // BigDecimal로 변경하는 함수
    public static BigDecimal toBigDecimal(Object obj) {
        if (obj == null) {
            return BigDecimal.ZERO;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        if (obj instanceof Number) {
            return BigDecimal.valueOf(((Number) obj).doubleValue()); // Number 타입 처리
        }
        try {
            return new BigDecimal(obj.toString().trim()); // 문자열로 변환 후 BigDecimal 생성
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + obj);
        }
    }

    public static boolean objectNullCheckOrStringIsEmpty(Object value) throws CustomResponseException {
        try {
            if (value == null || (value instanceof String && ((String) value).trim().equals("")))
                return true;
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean objectNullCheckOrIntegerEqZero(Object value) {
        try {
            if (value == null || Integer.parseInt(String.valueOf(value).trim()) == 0)
                return true;
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static String getNowLocalDataTime(boolean defaultYn) {
        LocalDateTime now = LocalDateTime.now();
        if (defaultYn)
            return now.toString();
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return now.format(formatter);
        }
    }

    // int 에 , 있을 경우 삭제 하고 double 로 변경
    public static Double stringToDoubleExistCommaDelete(Object stringTypeValue) {

        if (stringTypeValue == null) {
            throw new CustomResponseException("타입 변환 에러입니다. \n 숫자 타입이 아닙니다. ", 405);
        }

        try {
            return Double.parseDouble(String.valueOf(stringTypeValue).replaceAll(",", ""));
        } catch (Exception e) {
            throw new CustomResponseException("타입 변환 에러입니다. \n 숫자 타입이 아닙니다. ", 405);
        }
    }

    public static void settingCriteria(Map<String, Object> Map) {
        if (Map.get("perPageNum") == null || Map.get("page") == null || Map.isEmpty()) {
            throw new CustomResponseException("올바르지 않은 페이징 요청입니다.", 400);
        }
        Object perPageNum = Map.get("perPageNum");
        Object page = Map.get("page");
        int perPageNumIntValue = 1;
        int pageIntValue = 1;

        try {
            perPageNumIntValue = Integer.parseInt(String.valueOf(perPageNum));
            pageIntValue = Integer.parseInt(String.valueOf(page));
        } catch (Exception e) {
            perPageNumIntValue = 100000000;
            log.error(e.getMessage());
        } finally {
            Criteria cri = new Criteria(); // 페이징처리를위한 cri생성
            cri.setPerPageNum(perPageNumIntValue);
            cri.setPage(pageIntValue);
            Map.put("cri", cri);
        }
    }
}
