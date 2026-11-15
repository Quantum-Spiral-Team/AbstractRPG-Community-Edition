#!/usr/bin/env bash
set -euo pipefail

RUNDIR="${1:?usage: $0 <run-dir>}"
LOG="$RUNDIR/logs/latest.log"
MARKER="$RUNDIR/.started"

FATAL=(
  'Fatal errors were detected'
  'LoaderExceptionModCrash'
  'The state engine was in incorrect state ERRORED and forced into state SERVER_STOPPED'
  'Exception stopping the server'
)

fail() {
  echo "::error::$1"
  if [ -f "$LOG" ]; then
    echo "::group::latest.log"; cat "$LOG"; echo "::endgroup::"
  fi
  exit 1
}

shopt -s nullglob
crashes=("$RUNDIR"/crash-reports/crash*.txt)
if [ "${#crashes[@]}" -gt 0 ]; then
  cat "${crashes[-1]}" >&2
  fail "Crash report detected: ${crashes[-1]##*/}"
fi

if [ -f "$LOG" ]; then
  for pattern in "${FATAL[@]}"; do
    if grep -qF -- "$pattern" "$LOG"; then
      fail "Found in log: $pattern"
    fi
  done
fi

[ -f "$MARKER" ] || fail "Startup marker not found: server did not reach FMLServerStartedEvent"

echo "Server started and stopped cleanly"