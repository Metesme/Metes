package com.eternal.controller;

import com.eternal.common.utils.StringUtils;
import com.eternal.common.web.controller.BaseController;
import com.eternal.common.web.domain.AjaxResult;
import com.eternal.domain.UserEntity;
import com.eternal.model.UserInfo;
import com.eternal.service.TokenService;
import com.eternal.utils.AESUtils;
import com.eternal.utils.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 4:33 下午
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult login (@RequestBody UserEntity user) throws Exception {
        //测试数据
        String masterKey = "d888db18-b28c-4414-a284-f6e6082c816d";
        String password = "49BA59ABBE56E057";
        //用户密码加密 masterKey
        String masterKeyBa = "U2FsdGVkX1/EGsIhK0P0Fl4wnaZFEZxCe5JUcUeom3OTUJE4KndurNn1PUhXl0k2\n" +
                "NWbVVu8akhd9EHLtN7AOig==";

        String privateKeyBa = "U2FsdGVkX1/PxtpBYHPjCRPlqR6yp4qKlivaqysOUqF88TO1UPkaMACK1yhC7LbY\n" +
                "ft10iZqPFarYJf0SaepZmE08K8oNWrg9a9yOraAtcpgEhPhNxqH8FKLGk6W7pqM2\n" +
                "iqIlSefGOGflvuI7VKfyHCTnV5BUoHuVf2dAghikjZUAQGfQwkmtlRyI0NCgPsjO\n" +
                "OLxMeO9O2CSCNWkFGFlhLsXTV5BpTS4QfOllMsSqM1PqnYUreU0VCp2gg2uuRCHi\n" +
                "0BhXrSievxyVSNcX7Ta/rIvECbqUVJ8tSRgk6I6uXhDFGLtnSn0M/hJkeNRAq/wB\n" +
                "9iV4bk9BTrC+9T1EvCYCHqaY6lP5bjN5oO6DULSYNaxjxTJF2/XD8l6CIyI4PfXf\n" +
                "2NtJGBzKr6j21SUQc0fV45o0GXS0eUm16Kcb3ZObBOFSlYUgT31ZAOW7KYciYdEl\n" +
                "MDePWEd/kT6tAxNLnBnBHqw/rY23pMJfF6zrBPS0XShM7w89m99M4bTCESXnHovT\n" +
                "UM85Av4PjXO+0uNIt6FONOD3ZrqirBGKETuSTJLrsyMidWjtRUJFTxU+BILt10Yv\n" +
                "JkvPaD/ZeJqgnyyqLonEvQuxx3NM6a9eOgASTiuYukkPC5ALMZKnlboKM1dsJGTj\n" +
                "xoiw+zuO/yftU6UwFSCR7ZUBogz97fWxoLtPKKjV71ka3lImV7g1Lf/Y2Aqymc6i\n" +
                "RrWHDusJm5ZTX5WAMQMfBiegz3j9EqwQmqyyE+Eb0tT0ypihbGXe4vkrwF0Pzlqn\n" +
                "LEzaKlbRw6Bh2qGHmyvJ0albao7cdXTZ7I3mY5AJwVDggXbETHMemXpvvs5Z7xuT\n" +
                "cawU2Mhc2I66/dUUGoGzb8MxxQzgGCa+XN3WDgfnE7DUWl4ebmTeRSWkqFFGpUNW\n" +
                "+NDcwbEPrkfPivi9as79Go1SeP45nHoCxRg0sh5HHC2zlR5M2F0IADepKCY7dKBQ\n" +
                "JDZTyFtO696Duqg56cHuSZLwGv0bQhDxVRZLSlZWaL2dMu2IwnmA8arlm0j7YYGT\n" +
                "SdTqwHVPuXx0aks3RXKA3r+WCNB9cViE82WHzHZivZtBhiFSZ3zqkNj0v5C2XUmd\n" +
                "vqzQEeCvRJ92fhCGYTGToeaAaTxxpPp8Xs7K9pNKu4iVH39YQYU/HO8Nf8k2hePo\n" +
                "fKoULzXLAmcZ7r2CpuWJdZVsxGxsnR4eV2S/N3gTqB8jZWSQFQzC98UZ2pdvqvfv\n" +
                "EPotEVQgsKN+j85LRZTOg5LEgbQD7fKYgEe3doe+lPJ20f/kOAerbSyYnMO2lasO\n" +
                "qaj1AdECbVonwQD8IoDeY2Yxnm1xpp1MRbO1xKSzc9yPMR9GrtQLaKEp9ebfnuR+\n" +
                "Oi0BBFHQ4Cn4y1M2xLx5VHMXYrXTtXIJaq94vNAwOpDoy5rBQRaYPqlRPWvrFJi4\n" +
                "UEwAf+e45pHius16XIfvwTTx2DlL29Q5A5fSfO21sLM4TLfx7dA51bHrsixbC3BU\n" +
                "uOHBEKC4yI5IG+BfccSC5e/8I3QLnFmSJWuTBZT+FHrodGHl2pLOcrHA4ACTpVA/\n" +
                "D/wr+jD22xNPDaqdD+JtDCULzE3MbiA3MVxCHO3mR5wWEPnL7YTltpNPhziVPLLO\n" +
                "uMF0RKVkFSmLiziwQmmqtUvtbNiC+4gvkcM8RhqSgTuSXjPWzZBytPeT/dsRSDv/\n" +
                "H3Az7N8E6441b5Kqgc2DaAkJSiS+ZRy2kvJ3hGSXkBkr6jqqyR4JeZPgOZUgqVDC\n" +
                "pVYKg8+JpilqZtKsH3ZcYYq2enUO1Phxdavh9UimVW/rHk4AfuTYPHspsjAZpapi\n" +
                "yjy+2z0P2ZdF2RAkTw2bJG8PbE1qcGlS4VT+A1J8svddE+efq5ixEgnDMI9rUNXT\n" +
                "CHZGxLjzVPR4v4TU+/ASypU4Sxer5OuN4nztu6R9nW0XLhSoeC/wt4ihkqx67lf2\n" +
                "1t//XKQYrr3uoZK9IGLYJhgVczFsNPtmlJ9P1lz1mCCoaIRZoHwITpzlV5de/eE9\n" +
                "7FgT6YgN3K+fxzodvi05UBYkhhQup5o4IYx4rM86eY+qMjI67n5+g2/DsN+MGdaK\n" +
                "5+6qJyfddfdo9+CAJ4TPPqqUvfQ/rQ6LZS9ZnfhnDB5568jC5hthodsNrFEpv8nI\n" +
                "JmfW2w2lIsgto9hNqaCXUTt9ofAxNM4yswGivDWJXOwpCc21zBZgYrdfi8NNVA8Z\n" +
                "8gFoX2sB2rsU4f/UG3O5qhTFrQ/WybSj8YU7OMnO2cGdrB/toRhAy5Yz3u7jwA0O\n";
        String publicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuyTgkhptKKFipaEoXWRG\n" +
                "60D6whNUpZDKkEcvUx6WROKWhLi5Dg8X6r1Z4TSMR/emAGkzYG2ocLhNF4Giuiku\n" +
                "OjD8jtn2CFeOAE2tmloRKpD48EoetxUomdXdiI2mVe2sRRxwr7S7EOaICQ8xmKjo\n" +
                "bMORtqUn71255XRMdm+Pf3vDVh8UQWMv7rtL1fQeCnu9g9YYPHxmEvUzZPy0JFYR\n" +
                "7hxmnkVbAoipH4Y5ybM8sk0NXOCs2ZrziBWV6mdFa1Ehe4/ys1Ypfcl2EsaKA2E6\n" +
                "48hoVKRMNg2vRcbspe3BKjSpLRgXlMKJBjQ2PrM4Z/BhH58xputo5Z0CAdSxBZ/U\n" +
                "awIDAQAB\n" ;

        String privateKey =
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC7JOCSGm0ooWKl\n" +
                "oShdZEbrQPrCE1SlkMqQRy9THpZE4paEuLkODxfqvVnhNIxH96YAaTNgbahwuE0X\n" +
                "gaK6KS46MPyO2fYIV44ATa2aWhEqkPjwSh63FSiZ1d2IjaZV7axFHHCvtLsQ5ogJ\n" +
                "DzGYqOhsw5G2pSfvXbnldEx2b49/e8NWHxRBYy/uu0vV9B4Ke72D1hg8fGYS9TNk\n" +
                "/LQkVhHuHGaeRVsCiKkfhjnJszyyTQ1c4KzZmvOIFZXqZ0VrUSF7j/KzVil9yXYS\n" +
                "xooDYTrjyGhUpEw2Da9Fxuyl7cEqNKktGBeUwokGNDY+szhn8GEfnzGm62jlnQIB\n" +
                "1LEFn9RrAgMBAAECggEAC/hv5OiL0u1/wdhtHIwtfR7K618RKwX37wKepFJwijd0\n" +
                "KUknjR6qCPnTpfQWhQPyOlSAVMqu24buZ3KDViUML+yyOvD2K9Jt4tcVI6pfLrnX\n" +
                "MoeLL2rLDgv//5wP568sNb979cE5tfsI+ItgQMONGmfoCibGRylXmrW1rl20cBL+\n" +
                "VHysTHjldj2ae6Ehf0KlYADEmYdJMa6rdMJbH3cKEN7foKofLJvR/+Mgit+0+PoR\n" +
                "s1oEjq8I9TRozOZngC/8jukQGveWPwjvrEWPiChmP//bf496o8hic/eWo7zadCms\n" +
                "//3cLyON8oJArO+TTKtaxtHqyThh3xLRm9hFeMbGAQKBgQD3q8132eJ6t4zuEg7G\n" +
                "2sJwsClUPSsBfPLkGw5OpoTm0ZJJYwXdML0361sq6t4/lHt5BSeMp5utbGF+nbEZ\n" +
                "jmcDMQAQNWU5olkpDIxGvvJveZl0tX4ZvM0o3MsYAopFpqCx81G6yn65evTncuuu\n" +
                "hNxS8Vn3Fe1vSxdPxG5V1hlFkwKBgQDBb/98lCHPpmIbvFjQUqV5nJUIvhqCVBAp\n" +
                "8xpfhIZTqqZrbXY4CNFjyVnuv4yxWXrxUxlgJweZ4T0BuTfbVpg9oDQY9qG3whWC\n" +
                "cl2x0aC5UGfl+lN/dxz3+fVR/guna+Abw9wXmW0G3knchmPcMVQW14KwHYA6nSrI\n" +
                "y3P0hrd8yQKBgQCBDXd53QHDGgOIRcaHXZP1rgVLDLhtBRN3QpXY67HSqB8GuF9k\n" +
                "2zGtgqi1mz6FeLRB4iqygt9+pTvEAx02CNqqcgzrZTdCqdK1rgEmF/cxmPYaNzvm\n" +
                "41cv1KTK9P/hvSp8ryHGKB+SPHyCYYJnJHm9il9rfXSECmeyO9OWTIJMmwKBgQCY\n" +
                "cWsnApaizr0CnqxfgMJn1L6e5TJ/A0C5zpw6Knax+QquHtXL0ycxPL1jKhH2sk3x\n" +
                "YUveOuQCoT8y3ITolpXj+x0Tj0pjRtEsvHSabln7sSIwW9bx3S03QAXVWCBXEOzM\n" +
                "9M2+y2rgvs32vPJ11WovlieoI5eqmbHeDHiVfJWeAQKBgA2HKROS48F69/q5DMUf\n" +
                "nXCXvZZCHwpCh1wjs10Xtc5zApjCr7Lly2cxGq/09IGGP0cqyuqH6y6XuSRv3kIS\n" +
                "OuVBH4w1xswfiknKBNl3O2CfKXOGrrloniq4CYZluIBEgZm/ldnO2TTRXzYsHcdi\n" +
                "4BxOzJmqw0IlNfCtAs70BWsh\n" ;

        //masterKey 加密 peivKey


        AjaxResult result = new AjaxResult();
        if (StringUtils.isNotEmpty( user.getUserName())
                && StringUtils.isNotEmpty( user.getPassword())){
            if(user.getPassword().equals("233")){
                String token = tokenService.createToken(233L ,user.getUserName());

                //加密 token 发送到客户端解密

                String encrypt = RSAUtils.encrypt(token, publicKey);
                result.put("token",encrypt);
                result.put("masterKeyBa",masterKeyBa);
                result.put("privateKeyBa",privateKeyBa);
                return result;
            }
        }

        return result;
    }
}
