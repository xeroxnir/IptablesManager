BASEDIR=$(dirname $0)
PRODUCT_PATH=$1
echo $PRODUCT_PATH
java -Xmx1024m -jar $BASEDIR/../../../out/host/linux-x86/framework/signapk.jar $BASEDIR/../../../build/target/product/security/platform.x509.pem $BASEDIR/../../../build/target/product/security/platform.pk8 $BASEDIR/app/build/outputs/apk/app-release-unsigned.apk $PRODUCT_PATH/system/app/MYAPP.apk
