SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(force3uhd|force3uhdplus|tm4ksuper|tmtwin4k|lunix34k|galaxy4k|revo4k)$"

inherit kernel machine_kernel_pr samba_change_dialect

SRC_URI[md5sum] = "3b6d3fd2257b61789eebdebac5c597b2"
SRC_URI[sha256sum] = "eb56d7e99ab9e869b6abfb2a0463015e7d7b2e8610b7b9d05285edb8e8dfaf4f"

SRC_URI = "http://en3homeftp.net/pub/src/linux-${PV}.tar.xz \
	file://defconfig \
	file://${OPENVISION_BASE}/meta-openvision/recipes-linux/kernel-patches/kernel-add-support-for-gcc${VISIONGCCVERSION}.patch \
	file://date-time.patch \
	file://0001.remove_vtuner_index_check.patch \
	file://0001-Support-TBS-USB-drivers.patch \
	file://0001-STV-Add-PLS-support.patch \
	file://0001-STV-Add-SNR-Signal-report-parameters.patch \
	file://0001-stv090x-optimized-TS-sync-control.patch \
	file://blindscan2.patch \
	file://genksyms_fix_typeof_handling.patch \
	file://0001-tuners-tda18273-silicon-tuner-driver.patch \
	file://01-10-si2157-Silicon-Labs-Si2157-silicon-tuner-driver.patch \
	file://02-10-si2168-Silicon-Labs-Si2168-DVB-T-T2-C-demod-driver.patch \
	file://0003-cxusb-Geniatech-T230-support.patch \
	file://CONFIG_DVB_SP2.patch \
	file://dvbsky.patch \
	file://rtl2832u-2.patch \
	file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
	file://0003-uaccess-dont-mark-register-as-const.patch \
	"

S = "${WORKDIR}/linux"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

FILES_${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/zImage"

kernel_do_install_append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst_${KERNEL_PACKAGE_NAME}-image () {
        if [ -d /proc/stb ] ; then
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/${MTD_KERNEL}
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        true
}

pkg_postrm_${KERNEL_PACKAGE_NAME}-image () {
}

do_rm_work() {
}
