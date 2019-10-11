package com.mrl.ischool.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class XssShieldUtil
{
    private static List<Pattern> patterns = null; private static String[] words = {
        "[", "]", "{", "}", "^", "!", "~", "|", "'", "\"", "$", "@", "%", "&", "*", "(", ")", "+", ",", ".", ";", "?" };

    private static List<Object[]> getXssPatternList() {
        List ret = new ArrayList();

        ret.add(new Object[] { "<(no)?script[^>]*>.*?</(no)?script>", Integer.valueOf(2) });
        ret.add(new Object[] { "eval\\((.*?)\\)", Integer.valueOf(42) });
        ret.add(new Object[] { "expression\\((.*?)\\)", Integer.valueOf(42) });
        ret.add(new Object[] { "(javascript:|vbscript:|view-source:)*", Integer.valueOf(2) });
        ret.add(new Object[] { "<(\"[^\"]*\"|'[^']*'|[^'\">])*>", Integer.valueOf(42) });
        ret.add(new Object[] { "(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", Integer.valueOf(42) });
        ret.add(new Object[] { "<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+", Integer.valueOf(42) });
        return ret;
    }


    private static List<Pattern> getPatterns() {
        if (patterns == null) {

            List list = new ArrayList();

            String regex = null;
            Integer flag = null;
            int arrLength = 0;

            for (Object[] arr : getXssPatternList()) {
                arrLength = arr.length;
                for (int i = 0; i < arrLength; i++) {
                    regex = (String)arr[0];
                    flag = (Integer)arr[1];
                    list.add(Pattern.compile(regex, flag.intValue()));
                }
            }

            patterns = list;
        }

        return patterns;
    }

    public static String filter(String param) {
        for (int i = 0; i < words.length; i++) {
            while (param.indexOf(words[i]) != -1) {
                String param1 = param.substring(0, param.indexOf(words[i]));
                String param2 = param.substring(param.indexOf(words[i]) + 1, param.length());
                param = param1 + param2;
            }
        }
        return param;
    }

    public static String stripXss(String value) {
        if (StringUtils.isNotBlank(value)) {

            Matcher matcher = null;

            for (Pattern pattern : getPatterns()) {
                matcher = pattern.matcher(value);

                if (matcher.find())
                {
                    value = matcher.replaceAll("");
                }
            }

            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }




        return value;
    }
}
