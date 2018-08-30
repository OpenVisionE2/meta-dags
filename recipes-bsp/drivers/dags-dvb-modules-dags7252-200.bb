SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE}"

SRCDATE = "20180829"
KV = "3.14.28"
PV = "${KV}+${SRCDATE}"

SRC_URI[md5sum] = "afc6cdaf64f2d57d366111e0662b30a6"
SRC_URI[sha256sum] = "55b272227061a8ec35ffbe6a01936d6bf3ef78ddab2f49e1cc7beafb9bb6986f"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_7252S-200mm-${KV}-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_populate_sysroot[noexec] = "1"

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    install -m 0644 ${WORKDIR}/lib/modules/${KV}/extra/bcmlinuxdvb.ko ${D}/lib/modules/${KV}/extra/bcmlinuxdvb_${MACHINE}.ko
    install -d ${D}/${sysconfdir}/modules-load.d
    echo bcmlinuxdvb_${MACHINE} _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
