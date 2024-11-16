with import <nixpkgs> { };
mkShell {
  buildInputs = [
    python311Packages.python-lsp-server
    jdt-language-server
    jdk
    maven
    # clang-tools
  ];
}
