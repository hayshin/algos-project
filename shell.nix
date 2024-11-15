with import <nixpkgs> { };
mkShell {
  buildInputs = [
    python311Packages.python-lsp-server
    python311Packages.scalene
    jdt-language-server
    jdk
    maven
    # clang-tools
  ];
}
