# k3d

https://k3d.io/v5.7.2/

> k3d is a lightweight wrapper to run k3s (Rancher Lab’s minimal Kubernetes distribution) in docker.
> k3d makes it very easy to create single- and multi-node k3s clusters in docker, e.g. for local development on Kubernetes.

```sh
k3d cluster create \
  --config default.yml \
  --k3s-arg '--kubelet-arg=eviction-hard=imagefs.available<1%,nodefs.available<1%@agent:*' \
  --k3s-arg '--kubelet-arg=eviction-minimum-reclaim=imagefs.available=1%,nodefs.available=1%@agent:*'
```

