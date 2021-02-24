DESCRIPTION = "GLES/EGL files for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "16.1"

PR = "${SRCDATE}"

PROVIDES = "virtual/libgles2 virtual/egl"

SRC_URI = "http://en3homeftp.net/pub/down/libgles-arm-${SRCDATE}.tar.gz"

S = "${WORKDIR}/libgles"

do_configure() {
}

do_compile() {
}

do_install() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/libnxpl.so ${D}${libdir}/
	install -m 0755 ${S}/libnexus.so ${D}${libdir}/
	install -m 0755 ${S}/libv3ddriver.so ${D}${libdir}/
	install -m 0755 ${S}/libnxclient.so ${D}${libdir}/
	ln -s libv3ddriver.so ${D}${libdir}/libEGL.so
	ln -s libv3ddriver.so ${D}${libdir}/libGLESv2.so
	install -d ${D}${includedir}
	for d in EGL GLES GLES2 GLES3 KHR; do
		install -d ${D}${includedir}/$d
		for f in ${S}/include/$d/*.h; do
			install -m 0644 $f ${D}${includedir}/$d/
		done
	done
}

FILES_${PN} = "${libdir}/*"
FILES_${PN}-dev = "${incdir}*"

RPROVIDES_${PN} = "virtual/libgles2 virtual/egl libnxpl.so libnexus.so libv3ddriver.so libGLESv2.so libEGL.so libnxclient.so"