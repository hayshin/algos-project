with import <nixpkgs> { };
mkShell {
  buildInputs = [
    python311Packages.python-lsp-server
    linuxKernel.packages.linux_6_11.perf
    jdt-language-server
    jdk
    maven
    # clang-tools
  ];
}
