package org.neoneputxoindexer.model

import org.neoneputxoindexer.exception.HttpException
import br.com.simpli.model.EnglishLanguage
import br.com.simpli.model.RespException
import java.util.ArrayList
import java.util.Date
import org.junit.Test
import org.junit.Assert.*

/**
 * Tests Transaction
 * @author Simpli© CLI generator
 */
class TransactionTest {

    @Test(expected = HttpException::class)
    fun testValidateInHash() {
        val transaction = Transaction()
        transaction.idTypeFk = 1
        transaction.from = "1"
        transaction.idBlockFk = 1

        transaction.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateHashLength255() {
        val transaction = Transaction()
        transaction.idTypeFk = 1
        transaction.from = "1"
        transaction.idBlockFk = 1

        transaction.hash = "70qr25efhclenvenst4pm67ic5bdwi546rwmly7phsp5c2r0a7h4evyyh6hn74sggtd7wuc150huzu2bhhusob9qe91zb905trr9sbois2pz07vapwinjyj9iig9fzs7a4ekm0axuy2koaoskunqlliqh4dg1nawerj1d1mty5it8oq52py7ou6waawjd451vsyt8n4lgqva3a07aguwtzd3xkn7nplzrs5uty1l73wdqesxb1cr2s9zxdj3grjofx4t98vzwx5jkteefjztdrsucyy7rlwm2eot8ostkzxqinx70m5qrnwctv2z3uw95oxq6vlxzk1u7kuktlwreornxgj2it4xgin7ayuaizue27rt47rvk4s1xua5hitsyf7opsyar7hpyls9jsjtkw6q4wqly847bz8f1szk38gakl3f5pkf547gzb2w68d3tchltp41tah660ung3q7vuqpfwgts6uwvq5fopldhhhz4snx71nz8emgp8y2vi360ckmqsl05xgdd030ev9c40k4kb4oyh3woh60eco9o1f0ehxemn79xahayqwhwfu7ca19g4gh6kfqvpso53ll2e5b63ubdiax9k0tpr66ppu2i145dmc9ta878t8i2lu12tf4rb41q0drm7vcxd6ewsqflrp4744jopszwcwxw1g30i07pha7156qhaqt3hth2ma6dkxinis31jkb7wvtn9wp6izbibxurkhm4sw1wh0cwigg0febfv535ieif0xxmimeqb6grcmob0jmqu3s9tpyn3ulsovhijwe1otlwc889n9y31nohrnmf2dcfo1zm38xmoej1n6iqzknhyqvptk8gptf4u7285c42ienippk75bn2uxr11rvt9y8dgq12ro3gpjp01rss52az72qrzthy9oq2e9yt2497055jaeeov0j866q8kwnfrlcsjvgkr91aowl6et2pia6j0h5wsfhjf27ox9nh7ioemawy22wrzukkx5bepvq6a17nr0wzjjajnagvf531ipjkklgxtu7o7zvlq7fc9kmvxooqh3gngize4hxc6s1ex09ripoh6gqyn8457nzrlnlh7uwmaw90t5a6zd8hyj7qm0xqakuzhd07mdf7gfdmyerq02xdw0pdc0nd2ztphr0w276e0wqhziq02to7872oisizs7b2sbrj2pbv7k0t9wu6l76crl0oc42ye5jz2ukwu9tjvz57ngv657hq3gqc7wu6z9iruju3odf7ogjz0cgncttbl78efnptuety2dg1h4ty1vduwll6fw32wtyivrmgvb3u3ri7vt1fwgh4gt9m04v7wk0pflig0uigdnswrkgo5gcfvsgyxrc9pje1wwztajpcx3hgos1ikra95u339p2b3onc98b9ur00ueobn8yaptfi0x5rk91h55njmjxi6gj2o6y78d8oh5kvxouf1288jafamker3hqwjrpehrgg6hgusi7s111g3r2ncwo9ijlb4borsuik3vu8px144d1dpmasmawj22dpiw9z0agni92ytwrpfinovcwt5y1oibf4i39ykd6adj6siim2vjfct8v6hm5mp6d0yh35wj3mxvchmt1ohfcqvbxx6naqbiexem8o6xbdgl9l8erc7v67l2eug9pwh82kzwuzky4zvncfimc5b8xoqom8wl0yi9986dfbncsdpi4o2k9yahpn7kk9pjf3xwb2vqm9bzp70cyb7c92qdph4kdtv9i1vdwqt5xedptrd60v1rx1wjh9ujaufgs8tue5zb1oenaxwrmjdmmy8hp5qted3czjv9wydkfnpybytbn3hty4t5d2stfqhtngadv0ylyfeni46yx0tk4yxa401x49wujyjjmpitu4l78k0j97fvht6344kcoi3xwm7s7dodiwgvny8zr6mi6vh2nlcztcja8gunmv5axkbfgb29jdenuuzkpomvbzxb8f1nsbmqi1gnjazzp06q2m6gajqt318lc7s2i00k6gz11mc9z8hov7nceujauj1clgpx2rperd2hs7798ej9zrwe8tfpwe2d7dvd8c6lw9hnawgfu0pqj78blns0lynmasakvrbrmxaxjhdylyc2wrlmn2y6gc48pid8ofmkr7e1rl00ansmzbajwef54z994ygmdy8akepwslok16z4rs1izqtaof2av3h5rfeqpx1wbmh64xhailammmlqb51mzfzlu0dz9y49nwccgpddmb5o617dpqkqdg7zdwob9xn0bqrdr9m74lx3liv14uoxvuv3ngefgr39nbnkidpp7c96i1ggnatqbsnara6tfhemlqpikyinr4yjswkh9uut2hnr07xtzlu70izxdukebxdchddlhzx4vw68nmbhybye2xdo9zs6ay8xw5p3mz6p753tkh9whhd3hhshi37an3o2r1tye0x98jrhvtcg9mxfwg8dz5k7taoh3jpnw2c3xyo1m8mhsrc1ki2xqna8uuibagtdg0pi5wf3e4khyvbq95x1"

        transaction.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateInIdTypeFk() {
        val transaction = Transaction()
        transaction.hash = "1"
        transaction.from = "1"
        transaction.idBlockFk = 1

        transaction.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateInFrom() {
        val transaction = Transaction()
        transaction.hash = "1"
        transaction.idTypeFk = 1
        transaction.idBlockFk = 1

        transaction.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateFromLength255() {
        val transaction = Transaction()
        transaction.hash = "1"
        transaction.idTypeFk = 1
        transaction.idBlockFk = 1

        transaction.from = "z9ovnz15e22msf0tlq4u8kkvjxxzcnppm49szcenc1qpvdjxwp0uw49zcj66x05a3f1je0z4geio670gme8usdctfc9row6gkptphigtqwozyvlc0u7lzzpbjgg8poaympic1fvunyprzrbpgv0cddpiai9xn0wog5a6y0cdqcc3ha3bi6eumziinqxsnnlrl077ofp3am81h125f0sfjq2lrc14f7sbveu9dwcw2s0gwq6j9x3tyhe6buw5jb3bc4tt33c2eogy3hvda2du4g6k4gaziuc7mstnfrvf0jc2b4dpx2u33qa6yh295upluk7xa16dqqi5fwvqtaohr8i9do4de034ntrakwn1phvwjrjyvnn0zsr8cbbgcakdx0ige19t7qbamzxj6em2ug3swd7o71rs71n0txo5qp7qq6kffrpm3699p1j7lwjq82e9co5m4b0qyp44ak5qhzkocb1kzvs7f1tnab8h7qslpmlbmy520hspqv4yckcw08rid3r56xuhpdcy2bp4uck021rfapufsiyinynjc9flb5ox5l0mks1lalimm5k7rq0q4rc5290jf5yai75dxi76lmjp3tihszy8mholssrbp893vgmmgkja5515i93re9r1p4wfrclm5tr08bhmmri80x0j3ozk796dr3oud0mkg4f31z1bbvur1xfjip0v3wup6ylqydixv8mdd1pizwgqw9owgk1td1u7trx9uz6emtfxs7jmfktgprp2evjrsxuuv8zyr61r3wdxh8rx5uefyux8wwxsubfx93d2svfj060vlkquj400fhoia8g9esxankwsju7g5is3gm7y93w9vcv5kw0hezrdlf47pyzu9qef25auieqt60lc2kcwtqw4ciwup8budzaote06bm3nut5zerwqblb3mxzmu54k1lvw46eam01xns4onjx0dqinzuefs966l7jvsus8r8v9m4hwf78fxz71j18mh2lhxn49ua141bi4swkx49gbby2ep4cpaphgziwajhluxj7flhxzxsiwv2cwxrr64r5etmu9a72epwt5p3exuy7cp6mxsihwygmccxqw84vwogvu8ycsjajq1yqiwojy5r6ns79gf92yo60gd00bjlnbs7ev9f7b66dtr8r2qbu5k0o1sqt08fj99pzvu3guy52oa6t7ujqnopj2t7o2ckklilzusza978uxc4w3ecsp50h4owu0mjtmq9itbd7ncv0sqsesk680sgy2mhxbh6d4gwozox7utd83dd5h6oijkj8p2bqg0krrw3742v8uupu0dk9l4j1cw89kzmew6ykkwx47yol2ge559jqflnobmtxosbo9dy0wcyva1z9nn0z9d7pyi8j9tfj4fw0g3tr73o3nbzxtta02q3jjdkqnj6tn37ii59i9qu8x32p6mf3vkucpf4x1zvum48h68q0d7cg1gmf66yiwf4bup67kg6vzacwabeljihljwto9qxh0vp1lenztpjjs6yiml3s19n06f11vucan2ur2fsh8rcm7no2hl1wjnsl8ipc2fmoj0vn2s8o63anou2xj4qj1hbcrzae82co3ivp2t9yhqiwylbc0qnbn38rqnknvh03abs05lv7fcyignannla9od3g4rzn61v8v5ejegltmr8c0vagiwj8w69o75f0xp4ny79y0cz0z9ukp23vn6hk3ruauajjqp5zn3c745243n6rfssergpyzjidvpbb7zkpm49ji3qdu9z6xfoheaq8gnbbl4h1bldkrv4ceajpc36cfkidgsbbn8y2unl58ycbhbas65rvo3zlshy11d4a0bqsnmcv32wq1pre0r0sh9w58hkv19bmq3qiuf1837epc4ua5my688vb84uwh81qf65x27y8842bmlqpi5ra433l3818bmw0d1m69r5rr7zzuix0gn1zup9hykl2nci9u4q1r2yvq51gnhwmi5v4v5i26w4i281ue0nio452dusyqrm90vp1q9knxct4jpccth0xlol0k4d9gpqyzt195me5yg29nva7qshcnyc5p7q1i3jiowe76w3tfl4pkgf3urwdfp4788kkib9hm4o6bed7nby0lq3g1k3yo9jtnlwx6nj7rflexhyd1isoooen1hb9efh445u5mr3kmpjqjw0pkkcfebcx2shong104y4vs42hgaqakojynkwygzl05y77mwiy5o1vim6wstza4pjf6iwuwvtjj46w1hq73c4e1xwz0sww7n8cr9al2wqbiec7zbnas8iu18m5h4paq1es7tqida0936maya2p83w9u6btefyov61bgysqcbrq744p0ed2ii3kt5yifbtzbvgrmamrutlth1dlvryfhud5kkdgjoqrl2m1m6ypk39unwlienjghhtug2i9tdwavtjk6xpmsbyco9lig7fwdldsy0og48g4tk4yz8uxuylk8iynkd9ofvt1r5onoe8h8x891j351tx12xpbrdv0fiudxqbhozr3qwzc"

        transaction.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateInIdBlockFk() {
        val transaction = Transaction()
        transaction.hash = "1"
        transaction.idTypeFk = 1
        transaction.from = "1"

        transaction.validate(false, EnglishLanguage())
    }

    @Test
    fun testValidateSuccess() {
        val transaction = Transaction()
        transaction.idTransactionPk = 1
        transaction.hash = "1"
        transaction.idTypeFk = 1
        transaction.from = "1"
        transaction.idBlockFk = 1

        transaction.validate(false, EnglishLanguage())
    }
}
