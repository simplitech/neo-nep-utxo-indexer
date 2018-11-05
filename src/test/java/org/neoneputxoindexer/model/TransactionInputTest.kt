package org.neoneputxoindexer.model

import org.neoneputxoindexer.exception.HttpException
import br.com.simpli.model.EnglishLanguage
import br.com.simpli.model.RespException
import java.util.ArrayList
import java.util.Date
import org.junit.Test
import org.junit.Assert.*

/**
 * Tests TransactionInput
 * @author Simpli© CLI generator
 */
class TransactionInputTest {

    @Test
    fun testSetTransaction2Null() {
        val transactionInput = TransactionInput()
        transactionInput.transaction2 = Transaction()
        transactionInput.previousIdTransactionFk = null
        assertNull(transactionInput.transaction2)
        transactionInput.previousIdTransactionFk = 1L
        assertNotNull(transactionInput.transaction2)
    }

    @Test(expected = HttpException::class)
    fun testValidateInIdTransactionFk() {
        val transactionInput = TransactionInput()
        transactionInput.amount = 1
        transactionInput.spent = true
        transactionInput.to = "1"

        transactionInput.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateInAmount() {
        val transactionInput = TransactionInput()
        transactionInput.idTransactionFk = 1
        transactionInput.spent = true
        transactionInput.to = "1"

        transactionInput.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateInTo() {
        val transactionInput = TransactionInput()
        transactionInput.idTransactionFk = 1
        transactionInput.amount = 1
        transactionInput.spent = true

        transactionInput.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateToLength255() {
        val transactionInput = TransactionInput()
        transactionInput.idTransactionFk = 1
        transactionInput.amount = 1
        transactionInput.spent = true

        transactionInput.to = "mhtx0gz3xjs6x339jbabpq23lo9ykivgyamp8ft5nbyo8wir89s575z0f4zazwl4ehydaic9so3cdz7dfynos75gv6vj21umsxx1fwsuoby48ysxkqpyslpjmtb3ajflfo38tmjr102lvs8h88dktx3vbxby7va946ece7a5o6gz3r2p6vcw3n8su9b3sofbrdjgonhooie2gkv7jawsmp4hza1lxx7hwff1pkuby6plulpk9hjg0svqro5e9ppe00bn01em5rj7vts6dmwy10iw6lh1kej3npsxzcxzoa6mve5bn6u3bvn3fcvgqc28xebtoq3jyjxa6d20rndmsoe9lj6ugcw25x3fxy5zxu6asmpf1wsb9wyedfsn5zg722l39vis2iov4ufnbcjgu7f74ujulihqnhn376yjy8i4fpy6ql4ksoi40e0tmiv699n1awfo11fs2ea8t6qq8uxv1148c0cuaovfk3bjcxpxwkq0r2rmoh6sgva7z5yc8sva85icu5atk359y3ceku5vf2phrh1zyaa6eyeaf71ycc6whs8tldosi0bklj1hdo9rack7q5rywfjxtpu27zkbezee9rt9eabde2t1arn7i3wif05d0i7avzvzl59wmwvlnq8tlb0dtlay2glmcna4zxrxvbzbifovek4kzu0f0kebcf4bnu0y8vl56nrc09zmehtstwcc78vttxhunqwg268k666rlkpm0lbs1952iwp4gaaz7k7if3609gcd39cj75hcvj39b39fbnaazb391oxfw415cptu1ewzew7az5srx274c1w9v0yjtjblyru9kv9pmdk1b0txgz1jn7t32cbz3ixpuwbmfb3dlwwt0lcxfvntui4vso0cw9bo40xe6s4gshxw5762exlhh5enqp6gens6a1teoqjgh3appj0znzhkc4h4f9rszz3ixp1xz4nww0saoizser0kimicizemockip6lmsj0sszi1hi94ba21d5drhx8yxiylqwlxoqdg59awjw4meqd9j6ivkjh26djevebgzoqlrw74kjpfg60mevfo9xcsw50qtqd4fduncqtif69pnjvoyezz2bsu9d47ldn4htyy6vqv1qc4ekgjbo0fu4tmdebx271qv2mrh5wnididxfq2vk42isb5i286x1i7czuzma28drx5ssntrqnn6rhgwrb7anplry3k2v6j5k91mpkhp76a09lhp90lkh0kuh9hjfyvpgbe5qk1wrw8ov06pzm3wtm98k9g07f2t159vgpke0skr8bxrp3y87bnh2e2bfsoywqe16q0co8g7r5ckyxp4v1ynxyl4f311e15goad1nx5k75rc17xndhqc2lb3qhnaqvyp65jhxz05730c6v1fehq3o11kifqygq05a1mgkdb2s7ubrdfeduy4et5hxttipsjq047n62hbxwos11j9sn4od9ijglkcjlw6lu3z2eew8kgipkypypej608ygtqnm9xo2qg2lnowq3mxinh9dswcoum0tapqocuvdw4kn7b9ququ3ghn2o258ged91veto0jwwi4tgy4lxuhls96gi392hf57xznblj9j9b18zzarcnoi3kx8qg5mu0akv5stcnm9lbeplw1193bcais6h5qtdo7s5fvvu64xk0rayf9nxmivapmxouvu4ovfjvtws94aawyfagzqtz3b9ekx7bz8bv0193o5c7hn71f7nc55i79ukvpxvs6vokjvek6ntt1h4jk2gnp11hal3xri82fu9xsgosss1ix4bhzxzzz360ax6dqqmudz0hvurdryh3t23o061bm9tk7bztxjza2nhr82f796qqqr8qa01ted339zxjtqxnek3uswvtakrpiqfk92es9kjpibbr5bvowlxmg06e3n4l2f27p399k1bipg17s85gecmk82e5de1fp4hy7fber9dg93ethzoooqhgt0q5gxm6tsrnqobkau3pziravbkgboqwajnha6dy7c6vttjl17qymdk96kohhlt68bte24cpysntxlbsgdw5ym1mhnqc7uq7er28d7ntebjilekbarhsjpxc32m3g81kyx3mlf5r0otg649qe7gylfm4hs1l3y0iq3g3c9xpyz5zw1fqr607mswcm0dvq92c0ffn7u3d24vg8svwpd9fqhuz3i7vdzpfujhh9varbfyxnpqr2noqihsxd5qmc743gvyqc3gx80cbghbnpm56ven39fk67pvojcdjoxpz60a11hngnds3w7kbdlvvgrbs338esyy0txcevx9ebexf36mn43jwjh70m3u2gr7g44z9aa4rj6vgb00rehr5i32ym9121wfgadetv4e2y23xg4dze8yhudq7vz1vlmdy7v31wkef7njlj57pe2ehsuuu4rw4q2jp3832rz2hscgndkd8lm7b500kqw0cte6fdh5e9jh6nwht0kqjiydr39uuy8tb9mj33k8dk0brl89nttqnnugank6g54a93cwww0bfff6"

        transactionInput.validate(false, EnglishLanguage())
    }

    @Test
    fun testSetAssetNull() {
        val transactionInput = TransactionInput()
        transactionInput.asset = Asset()
        transactionInput.idAssetFk = null
        assertNull(transactionInput.asset)
        transactionInput.idAssetFk = 1L
        assertNotNull(transactionInput.asset)
    }

    @Test
    fun testValidateSuccess() {
        val transactionInput = TransactionInput()
        transactionInput.idTransactionInputPk = 1
        transactionInput.idTransactionFk = 1
        transactionInput.amount = 1
        transactionInput.spent = true
        transactionInput.to = "1"

        transactionInput.validate(false, EnglishLanguage())
    }
}
